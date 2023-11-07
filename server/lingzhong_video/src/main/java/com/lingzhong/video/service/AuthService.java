package com.lingzhong.video.service;


import com.lingzhong.video.bean.dto.AuthParamsDTO;
import com.lingzhong.video.bean.dto.UserExt;

/**
 * @Author: Ljx
 * @Date: 2023/2/24 11:55
 * @description 统一的认证接口
 */
public interface AuthService {

    /**
     * 统一的认证方法
     *
     * @param authParamsDto 认证类
     * @return 用户信息
     */
    UserExt execute(AuthParamsDTO authParamsDto);

}
