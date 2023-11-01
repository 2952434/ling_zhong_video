package com.lingzhong.video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.lingzhong.video.bean.dto.VideoPublishDTO;
import com.lingzhong.video.bean.po.User;
import com.lingzhong.video.bean.po.Video;
import com.lingzhong.video.bean.po.VideoData;
import com.lingzhong.video.bean.po.VideoLabel;
import com.lingzhong.video.bean.vo.RespBean;
import com.lingzhong.video.bean.vo.VideoVo;
import com.lingzhong.video.mapper.VideoLabelMapper;
import com.lingzhong.video.mapper.VideoMapper;
import com.lingzhong.video.service.VideoDataService;
import com.lingzhong.video.service.VideoService;
import com.lingzhong.video.utils.LoginUser;
import com.lingzhong.video.utils.TimeUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.persistent.FileRecorder;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
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
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private VideoMapper videoMapper;

    @Resource
    private VideoLabelMapper videoLabelMapper;

    @Resource
    private VideoDataService videoDataService;


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
        redisTemplate.expire(key,1, TimeUnit.HOURS);
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


}




