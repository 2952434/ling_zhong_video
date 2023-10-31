package com.lingzhong.video.service;

import com.lingzhong.video.bean.dto.UpdateUserDTO;
import com.lingzhong.video.bean.dto.UserRegisterDTO;
import com.lingzhong.video.bean.po.User;
import com.lingzhong.video.bean.vo.RespBean;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ljx
 * @description 针对表【user】的数据库操作Service
 * @createDate 2023-10-27 20:30:22
 */
public interface UserService {

    /**
     * 根据邮箱发送验证码
     *
     * @param mail 邮箱
     * @return 是否成功
     */
    boolean sentRegisterAuthCode(String mail);


    /**
     * 用户注册接口
     *
     * @param userRegisterDTO 用户注册信息
     * @return UserRegisterVo
     */
    RespBean<String> userRegister(UserRegisterDTO userRegisterDTO);

    /**
     * 邮箱登录时发送验证码
     *
     * @param mail 邮箱
     * @return 是否成功
     */
    boolean sentMailLoginAuthCode(String mail);

    /**
     * 根据用户id获取用户信息
     *
     * @param userId 用户id
     * @return User
     */
    User getUserById(Integer userId);


    /**
     * 上传用户头像
     * @param photo 用户头像
     * @return 地址
     */
    RespBean<String> uploadUserPhoto(MultipartFile photo);

    /**
     * 更新用户信息
     * @param updateUserDTO 用户信息
     * @return boolean
     */
    boolean updateUserInfo(UpdateUserDTO updateUserDTO);


}
