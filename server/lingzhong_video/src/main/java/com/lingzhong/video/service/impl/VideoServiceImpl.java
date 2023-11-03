package com.lingzhong.video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lingzhong.video.bean.dto.VideoEsDTO;
import com.lingzhong.video.bean.dto.VideoPublishDTO;
import com.lingzhong.video.bean.po.User;
import com.lingzhong.video.bean.po.Video;
import com.lingzhong.video.bean.po.VideoData;
import com.lingzhong.video.bean.po.VideoLabel;
import com.lingzhong.video.bean.vo.RespBean;
import com.lingzhong.video.bean.vo.VideoVo;
import com.lingzhong.video.mapper.VideoEsMapper;
import com.lingzhong.video.mapper.VideoLabelMapper;
import com.lingzhong.video.mapper.VideoMapper;
import com.lingzhong.video.service.VideoDataService;
import com.lingzhong.video.service.VideoService;
import com.lingzhong.video.utils.LoginUser;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author ljx
 * @description 针对表【video】的数据库操作Service实现
 * @createDate 2023-10-27 20:30:22
 */
@Service
public class VideoServiceImpl implements VideoService {

    private static final Logger logger = LoggerFactory.getLogger(VideoServiceImpl.class);

    @Resource
    private QiNiuService qiNiuService;

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private VideoMapper videoMapper;

    @Resource
    private VideoLabelMapper videoLabelMapper;

    @Resource
    private VideoDataService videoDataService;

    @Resource
    private VideoEsMapper videoEsMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean uploadVideo(MultipartFile file, VideoPublishDTO videoPublishDTO) throws Exception {
        try {
            User user = LoginUser.getUser();
            String videoUrl = qiNiuService.uploadVideo(file);
            Video video = new Video();
            BeanUtils.copyProperties(videoPublishDTO, video);
            video.setVideoUserId(user.getUserId());
            video.setVideoUrl(videoUrl);
            video.setVideoDate(new Date());
            video.setVideoSign(1);
            int insert = videoMapper.insert(video);
            System.out.println(insert);
            List<Integer> labelIds = videoPublishDTO.getLabelIds();
            for (Integer labelId : labelIds) {
                VideoLabel videoLabel = new VideoLabel();
                videoLabel.setVideoId(video.getVideoId());
                videoLabel.setLabelId(labelId);
                videoLabelMapper.insert(videoLabel);
            }
            VideoData videoData = new VideoData();
            videoData.setVideoId(video.getVideoId());
            videoDataService.insertVideoData(videoData);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("添加视频失败");
        }
        return true;
    }

    @Override
    public RespBean<String> deleteVideoById(Integer videoId) {
        User user = LoginUser.getUser();
        LambdaQueryWrapper<Video> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Video::getVideoUserId, user.getUserId()).eq(Video::getVideoId, videoId);
        Video video = videoMapper.selectOne(queryWrapper);
        if (video == null) {
            return RespBean.error("该视频不是您发表的删除失败");
        }
        String videoUrl = video.getVideoUrl();
        boolean deleted = qiNiuService.deleteFile(videoUrl);
        if (deleted) {
            videoMapper.deleteById(videoId);
            videoDataService.delVideoDataById(videoId);
            deleteVideoEsById(videoId);
            return RespBean.ok("删除视频成功");
        }
        return RespBean.error("删除视频失败");

    }

    @Override
    public List<VideoVo> getVideo(Integer page) {


//        TODO 算法需要优化
        return videoMapper.getVideo(page * 10);
    }

    @Override
    public List<VideoVo> getVideoByIp(String userIp) {
        String key = "browse:" + userIp;
        List<Object> rangeIds = redisTemplate.opsForList().range(key, 0, -1);
        List<Integer> videoIds = new ArrayList<>();
        if (rangeIds != null) {
            for (Object rangeId : rangeIds) {
                videoIds.add((Integer) rangeId);
            }
        }
        List<VideoVo> videoVos = videoMapper.getVideoByIp(videoIds);
        videoVos.forEach(item -> {
            Integer videoId = item.getVideoId();
            redisTemplate.opsForList().leftPush(key, videoId);
        });
        if (videoVos.size() < 10) {
            redisTemplate.delete(key);
        }
        redisTemplate.expire(key, 1, TimeUnit.HOURS);
        return videoVos;
    }

    @Override
    public Video getVideoById(Integer videoIds) {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("video_id", videoIds);
        return videoMapper.selectOne(queryWrapper);
    }

    @Override
    public List<VideoVo> getUserLikeVideoList(Integer userId) {
        return videoMapper.selectLikeVideoByUserId(userId);
    }

    @Override
    public List<VideoVo> getUserCollectVideoList(Integer userId) {
        return videoMapper.selectCollectVideoByUserId(userId);
    }

    @Override
    public List<VideoVo> getUserVideoByUserId(Integer userId, Integer page, Integer count) {
        return videoMapper.getUserVideoByUserId(userId, page * count, count);
    }

    @Override
    @Scheduled(cron = "0 */10 * * * ?")
    public void updateVideoEs() {
        List<VideoEsDTO> videoEsBeans = videoMapper.getVideoEsBeans();
        if (videoEsBeans.size() > 0) {
            videoEsMapper.saveAll(videoEsBeans);
            List<Integer> videoIds = new ArrayList<>();
            for (VideoEsDTO videoEsBean : videoEsBeans) {
                videoIds.add(videoEsBean.getVideoId());
            }
            videoMapper.updateVideoSign(videoIds);
        }
    }

    @Override
    public List<VideoVo> getVideoByEsAndHighLight(String content, Integer page, Integer count) {
        //根据一个值查询多个字段  并高亮显示  这里的查询是取并集，即多个字段只需要有一个字段满足即可
        //需要查询的字段
        BoolQueryBuilder boolQueryBuilder= QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("videoDescription",content))
                .should(QueryBuilders.matchQuery("videoAddress",content))
                .should(QueryBuilders.matchQuery("userName",content));
        Pageable pageable = PageRequest.of(page, 10);
        //构建高亮查询
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withPageable(pageable)
                .withHighlightFields(
                        new HighlightBuilder.Field("videoDescription"),
                        new HighlightBuilder.Field("videoAddress"),
                        new HighlightBuilder.Field("userName"))
                .withHighlightBuilder(new HighlightBuilder().preTags("<span style='color:red'>").postTags("</span>"))
                .build();
        //查询
        SearchHits<VideoEsDTO> search = elasticsearchRestTemplate.search(searchQuery, VideoEsDTO.class);
        //得到查询返回的内容
        List<org.springframework.data.elasticsearch.core.SearchHit<VideoEsDTO>> searchHits = search.getSearchHits();
        //设置一个最后需要返回的实体类集合
        List<VideoEsDTO> videoEsDTOS = new ArrayList<>();
        //遍历返回的内容进行处理
        for(SearchHit<VideoEsDTO> searchHit:searchHits){
            //高亮的内容
            Map<String, List<String>> highlightFields = searchHit.getHighlightFields();
            //将高亮的内容填充到content中
            searchHit.getContent().setVideoDescription(highlightFields.get("videoDescription")==null ? searchHit.getContent().getVideoDescription():highlightFields.get("videoDescription").get(0));
            searchHit.getContent().setVideoAddress(highlightFields.get("videoAddress")==null ? searchHit.getContent().getVideoAddress():highlightFields.get("videoAddress").get(0));
            searchHit.getContent().setUserName(highlightFields.get("userName")==null ? searchHit.getContent().getUserName():highlightFields.get("userName").get(0));
            //放到实体类中
            videoEsDTOS.add(searchHit.getContent());
        }
        List<VideoVo> videoVos = new ArrayList<>();
        for (VideoEsDTO videoEsDTO : videoEsDTOS) {
            VideoVo videoVo = videoMapper.getVideoVoById(videoEsDTO.getVideoId());
            BeanUtils.copyProperties(videoEsDTO,videoVo);
            videoVos.add(videoVo);
        }
        return videoVos;
    }

    @Override
    public void deleteVideoEsById(Integer videoId) {
        videoEsMapper.deleteById(videoId);
    }


}




