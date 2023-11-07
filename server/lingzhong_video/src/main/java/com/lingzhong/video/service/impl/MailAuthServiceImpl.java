package com.lingzhong.video.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lingzhong.video.bean.dto.AuthParamsDTO;
import com.lingzhong.video.bean.dto.UserExt;
import com.lingzhong.video.bean.po.User;
import com.lingzhong.video.mapper.UserMapper;
import com.lingzhong.video.service.AuthService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Random;


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
    public UserExt execute(AuthParamsDTO authParamsDto) {
        String key = String.format("%s:%s", authParamsDto.getAuthType(), authParamsDto.getUserMail());
        String code = redisTemplate.opsForValue().get(key);
        if (code == null || !code.equals(authParamsDto.getCheckCode())) {
            throw new RuntimeException("验证码错误");
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserMail, authParamsDto.getUserMail());
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            userRegister(authParamsDto.getUserMail());
        }
        user = userMapper.selectOne(queryWrapper);
        UserExt userExt = new UserExt();
        BeanUtils.copyProperties(user, userExt);
        redisTemplate.delete(key);
        return userExt;
    }


    public void userRegister(String userMail) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_mail", userMail);
        List<User> users = userMapper.selectList(queryWrapper);
        if (users == null || users.size() > 0) {
            throw new RuntimeException("该邮箱已经注册，请确认是否填写正确");
        }
        User user = new User();
        user.setUserMail(userMail);
        user.setUserName("凌众视频用户" + (new Random().nextInt(99999 - 10000 + 1) + 10000));
        user.setUserPhoto("http://s32t6kk2m.hb-bkt.clouddn.com/photo/8a6db7399545659e4983f4d042a28b95.jpeg");
        user.setUserSex(1);
        user.setUserDate(new Date());
        int insert = userMapper.insert(user);
        if (insert <= 0) {
            throw new RuntimeException("注册失败");
        }
    }
}
