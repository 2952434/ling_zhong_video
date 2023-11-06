package com.lingzhong.video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingzhong.video.bean.po.VideoLabel;
import com.lingzhong.video.bean.vo.VideoVo;
import com.lingzhong.video.service.VideoLabelService;
import com.lingzhong.video.mapper.VideoLabelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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

        return videoLabelMapper.getVideoByLabelId(labelId, page * 10);
    }


    @Override
    public List<Integer> getLabelIdsByVideoId(Integer videoId) {
        LambdaQueryWrapper<VideoLabel> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(VideoLabel::getVideoId,videoId);
        List<VideoLabel> videoLabels = videoLabelMapper.selectList(queryWrapper);
        return videoLabels.stream().map(VideoLabel::getLabelId).collect(Collectors.toList());
    }
}




