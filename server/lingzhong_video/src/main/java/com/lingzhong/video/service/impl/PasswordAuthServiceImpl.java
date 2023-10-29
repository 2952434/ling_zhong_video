package com.lingzhong.video.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lingzhong.video.bean.dto.AuthParamsDto;
import com.lingzhong.video.bean.dto.UserExt;
import com.lingzhong.video.bean.po.User;
import com.lingzhong.video.mapper.UserMapper;
import com.lingzhong.video.service.AuthService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @Author: 李君祥
 * @Date: 2023/10/29 18:00
 * @Description: 账号名密码方式
 */
@Service("password_authService")
public class PasswordAuthServiceImpl implements AuthService {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserMapper userMapper;


    @Override
    public UserExt execute(AuthParamsDto authParamsDto) {
        String userAccount = authParamsDto.getUserAccount();
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserAccount, userAccount);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new RuntimeException("账号不存在");
        }
        String userPassword = user.getUserPassword();
        String password = authParamsDto.getPassword();
        boolean matches = passwordEncoder.matches(password, userPassword);
        if (!matches) {
            throw new RuntimeException("账号或密码错误");
        }
        UserExt userExt = new UserExt();
        BeanUtils.copyProperties(user, userExt);
        return userExt;
    }

}
