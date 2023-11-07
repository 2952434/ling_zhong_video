package com.lingzhong.video.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: 李君祥
 * @Date: 2023/10/28 21:02
 * @Description: 标签返回类
 */
@Data
public class LabelVo {

    /**
     * 标签id
     */
    @ApiModelProperty(value = "标签id")
    private Integer labelId;

    /**
     * 标签名
     */
    @ApiModelProperty(value = "标签名", required = true)
    private String labelName;

    @ApiModelProperty(value = "是否是该用户添加")
    private Boolean isUserAdd;

}
