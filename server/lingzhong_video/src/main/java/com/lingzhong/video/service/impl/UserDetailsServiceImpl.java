package com.lingzhong.video.service.impl;

import com.alibaba.fastjson.JSON;
import com.lingzhong.video.bean.dto.AuthParamsDto;
import com.lingzhong.video.bean.dto.UserExt;
import com.lingzhong.video.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


/**
 * @Author: 李君祥
 * @Date: 2023/10/29 19:40
 * @Description: 邮箱验证码认证
 */
@Slf4j
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    ApplicationContext applicationContext;


    /**
     * @param s 传入的请求认证的参数就是AuthParamsDto
     * @return UserDetails
     * @throws UsernameNotFoundException 异常
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //将传入的json转成AuthParamsDto对象
        AuthParamsDto authParamsDto;
        try {
            authParamsDto = JSON.parseObject(s, AuthParamsDto.class);
        } catch (Exception e) {
            throw new RuntimeException("请求认证参数不符合要求");
        }

        //认证类型，有password，mail
        String authType = authParamsDto.getAuthType();

        //根据认证类型从spring容器取出指定的bean
        String beanName = authType + "_authService";
        AuthService authService = applicationContext.getBean(beanName, AuthService.class);
        //调用统一execute方法完成认证
        UserExt userExt = authService.execute(authParamsDto);
        //封装xcUserExt用户信息为UserDetails
        //根据UserDetails对象生成令牌
        UserDetails userPrincipal = getUserPrincipal(userExt);

        return userPrincipal;
    }

    /**
     * @param user 用户id，主键
     * @return 用户信息
     */
    public UserDetails getUserPrincipal(UserExt user) {
        //权限
        String[] authorities = {"test"};
        user.setUserPassword(null);
        //将用户信息转json
        String userJson = JSON.toJSONString(user);
        UserDetails userDetails = User.withUsername(userJson).password("123").authorities(authorities).build();
        return userDetails;
    }


}
