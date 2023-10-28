package com.lingzhong.video.controller;

import com.lingzhong.video.bean.dto.VideoLabelDTO;
import com.lingzhong.video.bean.po.Label;
import com.lingzhong.video.bean.vo.LabelVo;
import com.lingzhong.video.bean.vo.RespBean;
import com.lingzhong.video.service.LabelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Ljx
 * @Date: 2023/10/27 20:35
 * @role:
 */
@RestController
@Api(tags = "视频标签接口")
@RequestMapping("/label")
public class LabelController {


    @Autowired
    private LabelService labelService;

    @ApiOperation(value = "获得所有标签")
    @GetMapping("/getAllLabel")
    public RespBean<List<LabelVo>> getAllLabel() {
        List<LabelVo> labels = labelService.getAllLabel();
        return RespBean.ok(labels);
    }

    @ApiOperation(value = "根据id获取标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "labelId", value = "标签id", required = true, dataTypeClass = Integer.class, example = "1")
    })
    @GetMapping("/getAllLabel/{labelId}")
    public RespBean<Label> getLabelById(@PathVariable Integer labelId) {
        Label label = labelService.getLabelById(labelId);
        if (label == null) {
            return RespBean.error("没有查询到该值");
        }
        return RespBean.ok(label);
    }

    @ApiOperation(value = "新增标签")
    @PostMapping("/insertVideoLabel")
    public RespBean<String> insertVideoLabel(@RequestBody VideoLabelDTO videoLabelDTO){
        Boolean judge = labelService.insertVideoLabel(videoLabelDTO);
        if (judge) {
            return RespBean.ok("添加标签成功");
        }else {
            return RespBean.error("标签已存在");
        }
    }


}
