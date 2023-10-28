package com.lingzhong.video.service;

import com.lingzhong.video.bean.po.VideoData;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author ljx
* @description 针对表【video_data】的数据库操作Service
* @createDate 2023-10-27 21:36:13
*/
public interface VideoDataService extends IService<VideoData> {
    /**
     * 修改视频评论数
     */
    public Integer updateVideoCommentNum(Integer videoId , Integer addCommentNum);

    /**
     * 修改视频点赞数
     */
    public Integer updateVideoLikeNum(Integer videoId , Integer addLikeNum);

    /**
     * 修改视频收藏数
     */
    public Integer updateVideoCollectNum(Integer videoId , Integer addCollectNum);

    /**
     * 插入新视频数据
     */
    public Integer addNewVideoData(VideoData videoData);

    /**
     * 删除视频数据
     */
    public Integer delVideoDataById(Integer videoId);

    /**
     * 获取视频数据
     */
    public VideoData selectByVideoId(Integer videoId);

    /**
     * 初始化视频数据
     * @param videoData 视频数据
     * @return 是否成功
     */
    boolean insertVideoData(VideoData videoData);


}
