package com.lingzhong.video.bean.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: 李君祥
 * @Date: 2023/10/29 20:27
 * @Description: 授权信息
 */
@Data
@ApiModel(value = "用户认证信息")
public class AuthParamsDTO {

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "用户邮箱")
    private String userMail;

    @ApiModelProperty(value = "验证码")
    private String checkCode;

    @ApiModelProperty(value = "认证类型 password:用户名密码模式类型 mail:邮箱", example = "password")
    private String authType;

}