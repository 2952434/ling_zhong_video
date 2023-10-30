package com.lingzhong.video.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: 李君祥
 * @Date: 2023/10/29 14:37
 * @Description: 用户注册
 */
@Data
public class UserRegisterDTO {

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String userPassword;

    @ApiModelProperty(value = "邮箱，用于注册验证码验证")
    private String userMail;

    @ApiModelProperty(value = "验证码")
    private String authCode;
}
