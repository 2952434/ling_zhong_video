package com.lingzhong.video.service;

import com.lingzhong.video.bean.po.VideoData;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author ljx
* @description 针对表【video_data】的数据库操作Service
* @createDate 2023-10-27 21:36:13
*/
public interface VideoDataService{


    /**
     * 初始化视频数据
     * @param videoData 视频数据
     * @return 是否成功
     */
    boolean insertVideoData(VideoData videoData);

}
