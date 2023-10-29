package com.lingzhong.video.service;

import com.lingzhong.video.bean.dto.UserRegisterDTO;
import com.lingzhong.video.bean.po.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lingzhong.video.bean.vo.RespBean;
import com.lingzhong.video.bean.vo.UserRegisterVo;

/**
 * @author ljx
 * @description 针对表【user】的数据库操作Service
 * @createDate 2023-10-27 20:30:22
 */
public interface UserService {

    /**
     * 根据账号和邮箱发送验证码
     * @param account 账号
     * @param mail 邮箱
     * @return 是否成功
     */
    boolean sentAuthCode(String account, String mail);


    /**
     * 用户注册接口
     *
     * @param userRegisterDTO 用户注册信息
     * @return UserRegisterVo
     */
    RespBean<UserRegisterVo> userRegister(UserRegisterDTO userRegisterDTO);

}
