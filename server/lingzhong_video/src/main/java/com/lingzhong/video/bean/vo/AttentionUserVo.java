package com.lingzhong.video.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: 李君祥
 * @Date: 2023/10/30 21:50
 * @Description: 查询用户关注
 */
@Data
@ApiModel(value = "查询用户关注")
public class AttentionUserVo {

    @ApiModelProperty(value = "被关注的人id")
    private Integer userId;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "用户头像")
    private String userPhoto;

    @ApiModelProperty(value = "个性签名")
    private String userDescribe;

    @ApiModelProperty(value = "是否相互关注")
    private Boolean isBeAttention;

}
