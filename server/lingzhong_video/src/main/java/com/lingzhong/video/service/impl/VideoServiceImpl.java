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
import com.lingzhong.video.utils.FinalName;
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
import java.util.stream.Collectors;

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
//            推送视频
            Set<Object> members = redisTemplate.opsForSet().members(FinalName.USER_ATTENTION_KEY + user.getUserId());
            if (members != null && members.size() > 0) {
                for (Object member : members) {
                    redisTemplate.opsForSet().add(FinalName.VIDEO_WAREHOUSE_KEY + member.toString(), video.getVideoId());
                }
            }

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
//        删除文件
        boolean deleted = qiNiuService.deleteFile(videoUrl);
        if (deleted) {
//            删除视频
            videoMapper.deleteById(videoId);
//            删除视频数据
            videoDataService.delVideoDataById(videoId);
//            删除ES
            deleteVideoEsById(videoId);
//            删除推送
            Set<Object> members = redisTemplate.opsForSet().members(FinalName.USER_ATTENTION_KEY + user.getUserId());
            if (members != null && members.size() > 0) {
                for (Object member : members) {
                    redisTemplate.opsForSet().remove(FinalName.VIDEO_WAREHOUSE_KEY + member.toString(), videoId);
                }
            }

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
        if (rangeIds != null && rangeIds.size() > 0) {
            for (Object rangeId : rangeIds) {
                videoIds.add((Integer) rangeId);
            }
        }
        List<VideoVo> videoVos = videoMapper.getVideoByNotVideoIds(videoIds, 10);
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
    public List<VideoVo> recommendVideo(Integer count) {
        Integer userId = LoginUser.getUser().getUserId();
        String redisKey = FinalName.USER_BROWSE_VIDEO_KEY + userId;
//        根据关注推荐视频
        List<VideoVo> videoVoByVideoIds = getVideoVoByVideoIds(userId, (long) (count * 0.2), redisKey);
        List<VideoVo> ren = new ArrayList<>(videoVoByVideoIds);
//        根据点赞和收藏的视频类型推荐视频
        List<VideoVo> videoVoByLabelIds = getVideoVoByLabelIds(userId, (int) (count * 0.6), redisKey);
        ren.addAll(videoVoByLabelIds);

        //            忽略看过的视频
        Set<Object> members = redisTemplate.opsForSet().members(redisKey);
        List<Integer> ignoreVideoIds = new ArrayList<>();
        if (members != null && members.size() > 0) {
            ignoreVideoIds = members.stream().map(item -> (Integer) item).collect(Collectors.toList());
        }
//        根据点赞，收藏，评论的数量推荐视频
        List<VideoVo> videoByNotVideoIds = videoMapper.getVideoByNotVideoIds(ignoreVideoIds, count - ren.size());
        ren.addAll(videoByNotVideoIds);
        //            添加到用户看过的视频中
        videoByNotVideoIds.forEach(item -> {
            redisTemplate.opsForSet().add(redisKey, item.getVideoId());
        });
        redisTemplate.expire(redisKey, 10, TimeUnit.DAYS);
        if (ren.size() < count) {
            redisTemplate.delete(redisKey);
        }
        return ren;
    }


    public List<VideoVo> getVideoVoByLabelIds(Integer userId, Integer count, String redisKey) {
        List<VideoVo> videoVoByLabelIds = new ArrayList<>();
        Set<Object> top3Labels = redisTemplate.opsForZSet().range(FinalName.USER_LIKE_VIDEO_LABEL_KEY + userId, 0, 2);
        if (top3Labels != null && top3Labels.size() > 0) {
//            忽略看过的视频
            Set<Object> members = redisTemplate.opsForSet().members(redisKey);
            List<Integer> ignoreVideoIds = new ArrayList<>();
            if (members != null && members.size() > 0) {
                ignoreVideoIds = members.stream().map(item -> (Integer) item).collect(Collectors.toList());
            }
            List<Integer> top3LabelIds = top3Labels.stream().map(item -> (Integer) item).collect(Collectors.toList());
            videoVoByLabelIds = videoMapper.getVideoVoByLabelIds(top3LabelIds, count, ignoreVideoIds);
//            添加到用户看过的视频中
            videoVoByLabelIds.forEach(item -> {
                redisTemplate.opsForSet().add(redisKey, item.getVideoId());
            });
        }
        return videoVoByLabelIds;
    }


    public List<VideoVo> getVideoVoByVideoIds(Integer userId, long count, String redisKey) {
        //        关注的用户发布的视频ID集合
        List<Object> attentionVideoIds = redisTemplate.opsForSet().pop(FinalName.VIDEO_WAREHOUSE_KEY + userId, count);
        List<VideoVo> videoVoByVideoIds = new ArrayList<>();
        if (attentionVideoIds != null && attentionVideoIds.size() > 0) {
            List<Integer> videoIds = attentionVideoIds.stream().map(item -> (Integer) item).collect(Collectors.toList());
            videoVoByVideoIds = videoMapper.getVideoVoByVideoIds(videoIds);
//            添加到浏览过的视频集合中
            for (Integer videoId : videoIds) {
                redisTemplate.opsForSet().add(redisKey, videoId);
            }
        }
        return videoVoByVideoIds;
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
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("videoDescription", content))
                .should(QueryBuilders.matchQuery("videoAddress", content))
                .should(QueryBuilders.matchQuery("userName", content));
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
        for (SearchHit<VideoEsDTO> searchHit : searchHits) {
            //高亮的内容
            Map<String, List<String>> highlightFields = searchHit.getHighlightFields();
            //将高亮的内容填充到content中
            searchHit.getContent().setVideoDescription(highlightFields.get("videoDescription") == null ? searchHit.getContent().getVideoDescription() : highlightFields.get("videoDescription").get(0));
            searchHit.getContent().setVideoAddress(highlightFields.get("videoAddress") == null ? searchHit.getContent().getVideoAddress() : highlightFields.get("videoAddress").get(0));
            searchHit.getContent().setUserName(highlightFields.get("userName") == null ? searchHit.getContent().getUserName() : highlightFields.get("userName").get(0));
            //放到实体类中
            videoEsDTOS.add(searchHit.getContent());
        }
        List<VideoVo> videoVos = new ArrayList<>();
        for (VideoEsDTO videoEsDTO : videoEsDTOS) {
            VideoVo videoVo = videoMapper.getVideoVoById(videoEsDTO.getVideoId());
            BeanUtils.copyProperties(videoEsDTO, videoVo);
            videoVos.add(videoVo);
        }
        return videoVos;
    }

    @Override
    public void deleteVideoEsById(Integer videoId) {
        videoEsMapper.deleteById(videoId);
    }

    @Override
    public VideoVo getVideoVoByVideoId(Integer videoId) {
        return videoMapper.getVideoVoByVideoId(videoId);
    }


}




