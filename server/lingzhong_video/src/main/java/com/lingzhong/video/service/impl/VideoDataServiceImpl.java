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

}




