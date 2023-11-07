package com.lingzhong.video.service;

import com.lingzhong.video.bean.po.VideoCollect;

/**
 * @author ljx
 * @description 针对表【video_collect】的数据库操作Service
 * @createDate 2023-10-27 20:30:22
 */
public interface VideoCollectService {

    /**
     * 添加视频收藏信息
     *
     * @param videoCollect 视频收藏实体
     * @return Integer
     */
    Integer addNewVideoCollectData(VideoCollect videoCollect);

    /**
     * 删除视频收藏信息(用户取消收藏)
     *
     * @param videoId  视频Id
     * @param userId   用户Id
     * @param beUserId 被收藏的用户Id
     * @return Integer
     */
    Integer delVideoCollectData(Integer videoId, Integer userId, Integer beUserId);

    /**
     * 批量删除视频收藏信息(视频被删除)
     *
     * @param videoId 视频Id
     * @param userId  用户Id
     * @return Integer
     */
    Integer delVideoCollectList(Integer videoId, Integer userId);

    /**
     * 查看用户是否收藏
     *
     * @param userId  用户Id
     * @param videoId 视频Id
     * @return VideoCollect
     */
    VideoCollect selectByUserIdAndVideoId(Integer userId, Integer videoId);

}
