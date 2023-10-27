package com.lingzhong.video.controller;

import com.lingzhong.video.bean.po.Label;
import com.lingzhong.video.bean.vo.RespBean;
import com.lingzhong.video.service.LabelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Ljx
 * @Date: 2023/10/27 20:35
 * @role:
 */
@RestController
@Api(tags = "视频标签接口")
public class LabelController {


    @Autowired
    private LabelService labelService;

    @ApiOperation(value = "获得所有标签")
    @GetMapping("/getAllLabel")
    public RespBean getAllLabel() {
        List<Label> labels = labelService.getAllLabel();
        return RespBean.ok(labels);
    }

    @ApiOperation(value = "根据id获取标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "labelId", value = "标签id", required = true, dataTypeClass = Integer.class, example = "1")
    })
    @GetMapping("/getAllLabel/{labelId}")
    public RespBean getLabelById(@PathVariable Integer labelId) {
        Label label = labelService.getLabelById(labelId);
        if (label == null) {
            return RespBean.error("没有查询到该值");
        }
        return RespBean.ok(label);
    }


}
