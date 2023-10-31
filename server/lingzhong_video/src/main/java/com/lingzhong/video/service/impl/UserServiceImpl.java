package com.lingzhong.video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lingzhong.video.bean.dto.UserRegisterDTO;
import com.lingzhong.video.bean.po.User;
import com.lingzhong.video.bean.vo.RespBean;
import com.lingzhong.video.mapper.UserMapper;
import com.lingzhong.video.service.UserService;
import com.lingzhong.video.utils.SendMail;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ljx
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2023-10-27 20:30:22
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private SendMail sendMail;

    @Resource
    private UserMapper userMapper;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(4, 5, 10, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(2000), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());


    @Override
    public boolean sentRegisterAuthCode(String mail) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserMail, mail);
        User user = userMapper.selectOne(queryWrapper);
        if (user != null) {
            return false;
        }
        EXECUTOR.execute(() -> {
            try {
                String code = sendMail.sendSimpleMail(mail, "注册凌众视频验证码", String.format("您的账号 %s 正在注册凌众视频,验证码五分钟内有效 ", mail));
                String key = String.format("%s:%s", "register", mail);
                redisTemplate.opsForValue().set(key, code);
                redisTemplate.expire(key, 5, TimeUnit.MINUTES);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return true;
    }

    @Override
    public RespBean<String> userRegister(UserRegisterDTO userRegisterDTO) {
        String userMail = userRegisterDTO.getUserMail();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("user_mail", userMail);
        List<User> users = userMapper.selectList(queryWrapper);
        if (users == null || users.size() > 0) {
            return RespBean.error("该邮箱已经注册，请确认是否填写正确");
        }
        String key = String.format("%s:%s", "register", userMail);
        String code = redisTemplate.opsForValue().get(key);
        if (code == null) {
            return RespBean.error("未查询到发送的验证码，请重新发送");
        }
        if (!code.equals(userRegisterDTO.getAuthCode())) {
            return RespBean.error("验证码错误，请重新填写");
        }
        User user = new User();
        BeanUtils.copyProperties(userRegisterDTO, user);
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        user.setUserName("凌众视频用户" + (new Random().nextInt(99999 - 10000 + 1) + 10000));
        user.setUserPhoto("http://s32t6kk2m.hb-bkt.clouddn.com/photo/8a6db7399545659e4983f4d042a28b95.jpeg");
        user.setUserSex(1);
        user.setUserDate(new Date());
        int insert = userMapper.insert(user);
        if (insert <= 0) {
            return RespBean.error("注册失败");
        }
        redisTemplate.delete(key);
        return RespBean.ok("注册成功");
    }

    @Override
    public boolean sentMailLoginAuthCode(String mail) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserMail, mail);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            return false;
        }
        EXECUTOR.execute(() -> {
            try {
                String code = sendMail.sendSimpleMail(mail, "邮箱登录凌众视频验证码", "您正在登录 凌众视频,验证码五分钟内有效 ");
                String key = String.format("mail:%s", mail);
                redisTemplate.opsForValue().set(key, code);
                redisTemplate.expire(key, 5, TimeUnit.MINUTES);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return true;
    }

    @Override
    public User getUserById(Integer userId) {
        User user = userMapper.selectById(userId);
        return user;
    }
}




