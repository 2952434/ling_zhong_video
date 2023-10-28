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
     */
    public Integer addNewVideoCollectData(VideoCollect videoCollect);

    /**
     * 删除视频收藏信息(用户取消收藏)
     */
    public Integer delVideoCollectData(Integer videoId , Integer userId , Integer beUserId);

    /**
     * 批量删除视频收藏信息(视频被删除)
     */
    public Integer delVideoCollectList(Integer videoId , Integer userId);


}
