package com.lingzhong.video.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingzhong.video.bean.po.Label;
import com.lingzhong.video.service.LabelService;
import com.lingzhong.video.mapper.LabelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ljx
 * @description 针对表【label】的数据库操作Service实现
 * @createDate 2023-10-27 20:30:22
 */
@Service
public class LabelServiceImpl implements LabelService {

    @Autowired
    private LabelMapper labelMapper;

    @Override
    public List<Label> getAllLabel() {
        List<Label> labels = labelMapper.selectList(null);
        return labels;
    }

    @Override
    public Label getLabelById(Integer labelId) {
        Label label = labelMapper.selectById(labelId);
        return label;
    }
}




