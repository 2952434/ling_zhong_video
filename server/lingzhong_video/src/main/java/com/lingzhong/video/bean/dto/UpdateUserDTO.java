package com.lingzhong.video.bean.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: 李君祥
 * @Date: 2023/10/31 23:59
 * @Description: 更新用户传递的信息
 */
@Data
@ApiModel(value = "更新用户类")
public class UpdateUserDTO {

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String userName;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String userMail;

    /**
     * 用户头像
     */
    @ApiModelProperty(value = "用户头像")
    private String userPhoto;

    /**
     * 用户性别（0：女，1：男）
     */
    @ApiModelProperty(value = "用户性别（0：女，1：男）")
    private Integer userSex;

    /**
     * 用户描述
     */
    @ApiModelProperty(value = "用户描述")
    private String userDescribe;
}
