package com.lingzhong.video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lingzhong.video.bean.po.VideoCollect;
import com.lingzhong.video.service.VideoCollectService;
import com.lingzhong.video.mapper.VideoCollectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ljx
 * @description 针对表【video_collect】的数据库操作Service实现
 * @createDate 2023-10-27 20:30:22
 */
@Service
public class VideoCollectServiceImpl implements VideoCollectService {

    private final VideoCollectMapper videoCollectMapper;


    @Resource
    private VideoLikeServiceImpl videoLikeService;

    public VideoCollectServiceImpl(VideoCollectMapper videoCollectMapper) {
        this.videoCollectMapper = videoCollectMapper;
    }

    @Override
    public Integer addNewVideoCollectData(VideoCollect videoCollect) {
        Integer integer = videoCollectMapper.innerNewVideoCollect(videoCollect);
        if (integer > 0) {
            videoLikeService.addUserLikeVideoLabelScore(videoCollect.getVideoId(), 1);
        }
        return integer;
    }

    @Override
    public Integer delVideoCollectData(Integer videoId, Integer userId, Integer beUserId) {
        QueryWrapper<VideoCollect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("video_id", videoId)
                .eq("user_id", userId)
                .eq("be_user_id", beUserId);
        int delete = videoCollectMapper.delete(queryWrapper);
        if (delete > 0) {
            videoLikeService.addUserLikeVideoLabelScore(videoId, -1);
        }
        return delete;
    }

    @Override
    public Integer delVideoCollectList(Integer videoId, Integer userId) {
        QueryWrapper<VideoCollect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("video_id", videoId)
                .eq("user_id", userId);
        return videoCollectMapper.delete(queryWrapper);
    }

    @Override
    public VideoCollect selectByUserIdAndVideoId(Integer userId, Integer videoId) {
        QueryWrapper<VideoCollect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("video_id", videoId)
                .eq("user_id", userId);
        return videoCollectMapper.selectOne(queryWrapper);
    }
}




