package com.lingzhong.video.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingzhong.video.bean.po.VideoLabel;
import com.lingzhong.video.bean.vo.VideoVo;
import com.lingzhong.video.service.VideoLabelService;
import com.lingzhong.video.mapper.VideoLabelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ljx
 * @description 针对表【video_label】的数据库操作Service实现
 * @createDate 2023-10-27 20:30:22
 */
@Service
public class VideoLabelServiceImpl implements VideoLabelService {

    @Resource
    private VideoLabelMapper videoLabelMapper;

    @Override
    public List<VideoVo> getVideoByLabelId(Integer labelId, Integer page) {

        List<VideoVo> videoVos = videoLabelMapper.getVideoByLabelId(labelId, page * 10);

        return videoVos;
    }
}




