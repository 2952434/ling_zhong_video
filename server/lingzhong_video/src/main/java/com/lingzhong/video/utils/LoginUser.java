package com.lingzhong.video.utils;

import com.alibaba.fastjson.JSON;
import com.lingzhong.video.bean.po.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;


/**
 * @Author: 李君祥
 * @Date: 2023/10/29 21:54
 * @Description: 获得当前登录的用户信息
 */
@Slf4j
public class LoginUser {

    public static User getUser() {
        try {
            //拿 到当前用户身份
            Object principalObj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principalObj instanceof String) {
                //取出用户身份信息
                String principal = principalObj.toString();
                //将json转成对象
                return JSON.parseObject(principal, User.class);
            }
        } catch (Exception e) {
            log.error("获取当前登录用户身份出错:{}", e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("用户身份失效");
        }
        throw new RuntimeException("用户身份失效");
    }


}