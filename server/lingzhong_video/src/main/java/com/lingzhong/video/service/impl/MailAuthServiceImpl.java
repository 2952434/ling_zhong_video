package com.lingzhong.video.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lingzhong.video.bean.dto.AuthParamsDto;
import com.lingzhong.video.bean.dto.UserExt;
import com.lingzhong.video.bean.po.User;
import com.lingzhong.video.mapper.UserMapper;
import com.lingzhong.video.service.AuthService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @Author: 李君祥
 * @Date: 2023/10/29 19:58
 * @Description: 邮箱验证码认证
 */
@Service("mail_authService")
public class MailAuthServiceImpl implements AuthService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private UserMapper userMapper;

    @Override
    public UserExt execute(AuthParamsDto authParamsDto) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserMail, authParamsDto.getUserMail());
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new RuntimeException("账号不存在");
        }
        String key = String.format("%s:%s", authParamsDto.getAuthType(), authParamsDto.getUserMail());
        String code = redisTemplate.opsForValue().get(key);
        if (code == null || !code.equals(authParamsDto.getCheckCode())) {
            throw new RuntimeException("验证码错误");
        }
        UserExt userExt = new UserExt();
        BeanUtils.copyProperties(user, userExt);
        redisTemplate.delete(key);
        return userExt;
    }
}
