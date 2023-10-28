package com.lingzhong.video.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingzhong.video.bean.po.VideoData;
import com.lingzhong.video.service.VideoDataService;
import com.lingzhong.video.mapper.VideoDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ljx
 * @description 针对表【video_data】的数据库操作Service实现
 * @createDate 2023-10-27 21:36:13
 */
@Service
public class VideoDataServiceImpl implements VideoDataService {

    @Resource
    private VideoDataMapper videoDataMapper;

    @Override
    public boolean insertVideoData(VideoData videoData) {
        int insert = videoDataMapper.insert(videoData);
        return insert > 0;
    }
}




