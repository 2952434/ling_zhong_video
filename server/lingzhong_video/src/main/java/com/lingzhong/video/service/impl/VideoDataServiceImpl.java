package com.lingzhong.video.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingzhong.video.bean.po.VideoData;
import com.lingzhong.video.service.VideoDataService;
import com.lingzhong.video.mapper.VideoDataMapper;
import org.springframework.stereotype.Service;

/**
* @author ljx
* @description 针对表【video_data】的数据库操作Service实现
* @createDate 2023-10-27 21:36:13
*/
@Service
public class VideoDataServiceImpl extends ServiceImpl<VideoDataMapper, VideoData>
    implements VideoDataService{

    private VideoDataMapper videoDataMapper;

    public VideoDataServiceImpl(VideoDataMapper videoDataMapper) {
        this.videoDataMapper = videoDataMapper;
    }

    @Override
    public Integer updateVideoCommentNum(Integer videoId, Boolean isComment) {
        return videoDataMapper.updateVideoDataByFiled("vide_comment_num" , videoId , isComment);
    }

    @Override
    public Integer updateVideoLikeNum(Integer videoId, Boolean isLike) {
        return videoDataMapper.updateVideoDataByFiled("vide_like_num" , videoId , isLike);
    }

    @Override
    public Integer updateVideoCollectNum(Integer videoId, Boolean isCollect) {
        return videoDataMapper.updateVideoDataByFiled("vide_collect_num" , videoId , isCollect);
    }

    @Override
    public Integer addNewVideoData(VideoData videoData) {
        return videoDataMapper.insert(videoData);
    }

    @Override
    public Integer delVideoDataById(Integer videoId) {
        return videoDataMapper.deleteById(videoId);
    }

    @Override
    public VideoData selectByVideoId(Integer videoId) {
        return videoDataMapper.selectById(videoId);
    }

    @Override
    public boolean insertVideoData(VideoData videoData) {
        int insert = videoDataMapper.insert(videoData);
        return insert > 0;
    }
}




