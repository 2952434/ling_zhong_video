package com.lingzhong.video.service;

import com.lingzhong.video.bean.po.Label;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author ljx
* @description 针对表【label】的数据库操作Service
* @createDate 2023-10-27 20:30:22
*/
public interface LabelService {

    /**
     * 获取所有视频标签
     * @return 标签集合类
     */
    List<Label> getAllLabel();

    /**
     * 根据标签id获取标签
     * @param labelId 标签id
     * @return 标签类
     */
    Label getLabelById(Integer labelId);
}
