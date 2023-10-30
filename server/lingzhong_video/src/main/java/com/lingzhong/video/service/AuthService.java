package com.lingzhong.video.service;


import com.lingzhong.video.bean.dto.AuthParamsDTO;
import com.lingzhong.video.bean.dto.UserExt;

/**
 * @author Mr.M
 * @version 1.0
 * @description 统一的认证接口
 * @date 2023/2/24 11:55
 */
public interface AuthService {

    /**
     *
     * @param authParamsDto 认证类
     * @return 用户信息
     */
    UserExt execute(AuthParamsDTO authParamsDto);

}
