package com.lingzhong.video.service;

import com.lingzhong.video.bean.po.VideoLike;

/**
 * @author ljx
 * @description 针对表【video_like】的数据库操作Service
 * @createDate 2023-10-27 20:30:22
 */
public interface VideoLikeService {

    /**
     * 添加点赞记录
     *
     * @param videoLike 视频点赞实体
     * @return Integer
     */
    Integer addNewVideoLikeData(VideoLike videoLike);

    /**
     * 删除点赞记录
     *
     * @param videoId  视频Id
     * @param userId   用户Id
     * @param beUserId 被点赞的用户Id
     * @return Integer
     */
    Integer delVideoLikeData(Integer videoId, Integer userId, Integer beUserId);

    /**
     * 批量删除点赞记录
     *
     * @param videoId  视频Id
     * @param beUserId 被点赞的用户Id
     * @return Integer
     */
    Integer delVideoLikeList(Integer videoId, Integer beUserId);

    /**
     * 查看用户是否点赞
     *
     * @param userId  用户Id
     * @param videoId 视频Id
     * @return VideoLike
     */
    VideoLike selectByUserIdAndVideoId(Integer userId, Integer videoId);
}
