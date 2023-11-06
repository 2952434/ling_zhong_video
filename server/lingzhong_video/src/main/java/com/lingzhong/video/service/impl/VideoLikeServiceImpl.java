package com.lingzhong.video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lingzhong.video.bean.po.VideoLike;
import com.lingzhong.video.mapper.VideoLikeMapper;
import com.lingzhong.video.service.VideoLabelService;
import com.lingzhong.video.service.VideoLikeService;
import com.lingzhong.video.utils.FinalName;
import com.lingzhong.video.utils.LoginUser;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ljx
 * @description 针对表【video_like】的数据库操作Service实现
 * @createDate 2023-10-27 20:30:22
 */
@Service
public class VideoLikeServiceImpl implements VideoLikeService {

    private final VideoLikeMapper videoLikeMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private VideoLabelService videoLabelService;


    public VideoLikeServiceImpl(VideoLikeMapper videoLikeMapper) {
        this.videoLikeMapper = videoLikeMapper;
    }

    @Override
    public Integer addNewVideoLikeData(VideoLike videoLike) {
        Integer like = videoLikeMapper.innerNewVideoLike(videoLike);
        if (like > 0) {
            addUserLikeVideoLabelScore(videoLike.getVideoId(), 1);
        }
        return like;
    }

    public void addUserLikeVideoLabelScore(Integer videoId, Integer addScore) {
        //            添加用户喜欢视频的标签的分数，用于视频推荐
        List<Integer> labelIds = videoLabelService.getLabelIdsByVideoId(videoId);
        for (Integer labelId : labelIds) {
            redisTemplate.opsForZSet().incrementScore(FinalName.USER_LIKE_VIDEO_LABEL_KEY + LoginUser.getUser().getUserId(), labelId, addScore);
        }
    }


    @Override
    public Integer delVideoLikeData(Integer videoId, Integer userId, Integer beUserId) {
        QueryWrapper<VideoLike> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("video_id", videoId)
                .eq("user_id", userId)
                .eq("be_user_id", beUserId);
        int delete = videoLikeMapper.delete(queryWrapper);
        if (delete > 0) {
            addUserLikeVideoLabelScore(videoId, -1);
        }
        return delete;
    }

    @Override
    public Integer delVideoLikeList(Integer videoId, Integer beUserId) {
        QueryWrapper<VideoLike> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("video_id", videoId)
                .eq("be_user_id", beUserId);
        return videoLikeMapper.delete(queryWrapper);
    }

    @Override
    public VideoLike selectByUserIdAndVideoId(Integer userId, Integer videoId) {
        QueryWrapper<VideoLike> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("video_id", videoId)
                .eq("user_id", userId);
        return videoLikeMapper.selectOne(queryWrapper);
    }
}




