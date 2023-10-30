package com.lingzhong.video.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author: 李君祥
 * @Date: 2023/10/29 19:08
 * @Description: 安全管理配置
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    DaoAuthenticationProviderCustom daoAuthenticationProviderCustom;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProviderCustom);
    }

    /**
     *
     * @param http 配置安全拦截机制
     * @throws Exception 未知异常
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //访问/r开始的请求需要认证通过
                .antMatchers("/r/**").authenticated()
                //其它请求全部放行
                .anyRequest().permitAll()
                .and()
                //登录成功跳转到/login-success
                .formLogin().successForwardUrl("/login-success").and().cors();

    }

    @Override
    public void configure(WebSecurity web) {
//        放行所有请求
        web.ignoring().antMatchers("/video/getVideo/**");
    }

    public static void main(String[] args) {
        String password = "123456";
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        for (int i = 0; i < 5; i++) {
            //生成密码
            String encode = passwordEncoder.encode(password);
            System.out.println(encode);
            //校验密码,参数1是输入的明文 ，参数2是正确密码加密后的串
            boolean matches = passwordEncoder.matches(password, encode);
            System.out.println(matches);
        }

        boolean matches = passwordEncoder.matches("1234", "$2a$10$fb2RlvFwr9HsRu9vH1OxCu/YiMRw6wy5UI6u3s0A.0bVSuR1UqdHK");
        System.out.println(matches);
    }


}

