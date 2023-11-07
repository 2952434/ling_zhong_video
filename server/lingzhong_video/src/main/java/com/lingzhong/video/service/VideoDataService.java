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
     *
     * @param videoId       视频Id
     * @param addCommentNum 增加评论的数量
     * @return Integer
     */
    Integer updateVideoCommentNum(Integer videoId, Integer addCommentNum);

    /**
     * 修改视频点赞数
     *
     * @param videoId    视频Id
     * @param addLikeNum 增加点赞的数量
     * @return Integer
     */
    Integer updateVideoLikeNum(Integer videoId, Integer addLikeNum);

    /**
     * 修改视频收藏数
     *
     * @param videoId       视频Id
     * @param addCollectNum 增加收藏的数量
     * @return Integer
     */
    Integer updateVideoCollectNum(Integer videoId, Integer addCollectNum);

    /**
     * 插入新视频数据
     *
     * @param videoData 视频数据实体
     * @return Integer
     */
    Integer addNewVideoData(VideoData videoData);

    /**
     * 删除视频数据
     *
     * @param videoId 视频Id
     * @return Integer
     */
    Integer delVideoDataById(Integer videoId);

    /**
     * 获取视频数据
     *
     * @param videoId 视频Id
     * @return VideoData
     */
    VideoData selectByVideoId(Integer videoId);

    /**
     * 初始化视频数据
     *
     * @param videoData 视频数据
     * @return 是否成功
     */
    boolean insertVideoData(VideoData videoData);


}
