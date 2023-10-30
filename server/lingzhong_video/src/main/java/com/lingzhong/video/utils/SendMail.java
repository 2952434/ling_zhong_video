package com.lingzhong.video.utils;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Random;

/**
 * @Author: 李君祥
 * @Date: 2023/10/28 23:54
 * @Description: 发送邮件
 */
@Component
public class SendMail {

    @Resource
    JavaMailSender javaMailSender;

    int max = 999999;
    int min = 100000;
    Random random = new Random();

    /**
     * 忘记密码时验证邮箱
     *
     * @param email 被发送的邮箱
     * @return String
     */
    public String sendSimpleMail(String email,String title,String content) {
        int s = random.nextInt(max - min + 1) + min;
        String code = String.valueOf(s);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(title);
        message.setFrom("2952434583@qq.com");
        message.setTo(email);
        message.setSentDate(new Date());
        message.setText(content + "验证码是：" + code);
        javaMailSender.send(message);
        return code;
    }
}
    