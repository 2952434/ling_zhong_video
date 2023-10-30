package com.lingzhong.video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lingzhong.video.bean.dto.UserExt;
import com.lingzhong.video.bean.dto.VideoLabelDTO;
import com.lingzhong.video.bean.po.Label;
import com.lingzhong.video.bean.po.User;
import com.lingzhong.video.bean.vo.LabelVo;
import com.lingzhong.video.mapper.LabelMapper;
import com.lingzhong.video.service.LabelService;
import com.lingzhong.video.utils.LoginUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<LabelVo> getAllLabel() {
        User user = LoginUser.getUser();
        QueryWrapper<Label> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",user.getUserId()).or().eq("user_id",-1);
        List<Label> labels = labelMapper.selectList(queryWrapper);
        List<LabelVo> labelVos = new ArrayList<>();
        for (Label label : labels) {
            LabelVo labelVo = new LabelVo();
            BeanUtils.copyProperties(label,labelVo);
            if (label.getUserId() == -1) {
                labelVo.setIsUserAdd(Boolean.FALSE);
            }else {
                labelVo.setIsUserAdd(Boolean.TRUE);
            }
            labelVos.add(labelVo);
        }
        return labelVos;
    }

    @Override
    public Label getLabelById(Integer labelId) {
        Label label = labelMapper.selectById(labelId);
        return label;
    }

    @Override
    public Boolean insertVideoLabel(VideoLabelDTO videoLabelDTO) {
        boolean exists = labelExists(videoLabelDTO);
        if (exists) {
            return false;
        }
        Label label = new Label();
        Boolean isUser = videoLabelDTO.getIsUser();
        label.setLabelName(videoLabelDTO.getLabelName());
        if (isUser) {
            User user = LoginUser.getUser();
            label.setUserId(user.getUserId());
        }else {
            label.setUserId(-1);
        }
        int insert = labelMapper.insert(label);
        return insert > 0;
    }

    /**
     * 判断标签是否存在
     * @param videoLabelDTO 添加的标签
     * @return true：存在 false：不存在
     */
    private boolean labelExists(VideoLabelDTO videoLabelDTO) {
        Boolean isUser = videoLabelDTO.getIsUser();
        QueryWrapper<Label> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("label_name", videoLabelDTO.getLabelName());
        if (isUser) {
            User user = LoginUser.getUser();
            queryWrapper.eq("user_id",user.getUserId());
        }else {
            queryWrapper.eq("user_id",-1);
        }
        Label label = labelMapper.selectOne(queryWrapper);
        return label != null;
    }


}




