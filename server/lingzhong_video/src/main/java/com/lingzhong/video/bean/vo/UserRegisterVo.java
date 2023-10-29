package com.lingzhong.video.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: 李君祥
 * @Date: 2023/10/29 16:21
 * @Description: 注册成功返回账号密码，用于登录
 */
@Data
@ApiModel(value = "注册成功返回账号密码，用于登录")
public class UserRegisterVo {

    @ApiModelProperty("手机号")
    private String userAccount;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String userPassword;

}
