package com.lingzhong.video.controller;

import com.lingzhong.video.bean.dto.UserRegisterDTO;
import com.lingzhong.video.bean.vo.RespBean;
import com.lingzhong.video.bean.vo.UserRegisterVo;
import com.lingzhong.video.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: 李君祥
 * @Date: 2023/10/29 15:58
 * @Description: 用户操作类
 */
@Api(tags = "用户操作接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @ApiOperation(value = "根据手机号和邮箱发送验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "手机号", required = true, dataTypeClass = Integer.class, example = "xxx"),
            @ApiImplicitParam(name = "mail", value = "邮箱", required = true, dataTypeClass = Integer.class, example = "xxx@qq.com")
    })
    @GetMapping("/sentAuthCode")
    public RespBean<String> sentAuthCode(String account, String mail) {
        boolean judge = userService.sentAuthCode(account, mail);
        if (judge) {
            return RespBean.ok("验证码发送成功");
        } else {
            return RespBean.error("验证码发送错误");
        }
    }


    @ApiOperation(value = "用户注册接口")
    @GetMapping("/userRegister")
    public RespBean<UserRegisterVo> userRegister(UserRegisterDTO userRegisterDTO) {
        return userService.userRegister(userRegisterDTO);
    }


}
