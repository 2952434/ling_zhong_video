package com.lingzhong.video.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @Author: 李君祥
 * @Date: 2023/10/28 20:25
 * @Description: 添加标签
 */
@Data
public class VideoLabelDTO {

    @ApiModelProperty(value = "标签名", required = true)
    private String labelName;

    @ApiModelProperty(value = "如果是用户添加的传True，管理员添加传False", required = true, example = "false")
    private Boolean isUser;
}
