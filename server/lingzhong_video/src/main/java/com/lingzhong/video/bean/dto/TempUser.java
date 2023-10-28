package com.lingzhong.video.bean.dto;

import lombok.Data;

import java.util.Date;

/**
 * @Author: 李君祥
 * @Date: 2023/10/28 20:27
 * @Description: TODO:类的描述信息
 */
@Data
public class TempUser {

    /**
     * 用户主键
     */
    private Integer userId = 1;

    /**
     * 用户名
     */
    private String userName = "凌众";

    /**
     * 账号
     */
    private String userAccount = "saghdgsad";

    /**
     * 用户头像
     */
    private String userPhoto = "http://s32t6kk2m.hb-bkt.clouddn.com/photo/8a6db7399545659e4983f4d042a28b95.jpeg";

    /**
     * 用户性别（0：女，1：男）
     */
    private Integer userSex = 1;

    /**
     * 用户描述
     */
    private String userDescribe = "凌众视频真不错";

    /**
     * 账号创建时间
     */
    private Date userDate = new Date();

}
