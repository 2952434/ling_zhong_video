package com.lingzhong.video.controller;

import com.lingzhong.video.bean.dto.UpdateUserDTO;
import com.lingzhong.video.bean.dto.UserExt;
import com.lingzhong.video.bean.dto.UserRegisterDTO;
import com.lingzhong.video.bean.po.User;
import com.lingzhong.video.bean.vo.RespBean;
import com.lingzhong.video.service.UserService;
import com.lingzhong.video.utils.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @Deprecated
    @ApiOperation(value = "注册：根据手机号和邮箱发送验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mail", value = "邮箱", required = true, dataTypeClass = String.class, example = "xxx@qq.com")
    })
    @PostMapping("/sentRegisterAuthCode")
    public RespBean<String> sentRegisterAuthCode(String mail) {
        boolean judge = userService.sentRegisterAuthCode(mail);
        if (judge) {
            return RespBean.ok("验证码发送成功");
        } else {
            return RespBean.error("该邮箱已注册可直接进行邮箱登录");
        }
    }

    @Deprecated
    @ApiOperation(value = "用户注册接口")
    @GetMapping("/userRegister")
    public RespBean<String> userRegister(UserRegisterDTO userRegisterDTO) {
        return userService.userRegister(userRegisterDTO);
    }


    @ApiOperation(value = "邮箱登录时发送验证码")
    @ApiImplicitParam(name = "mail", value = "邮箱", required = true, dataTypeClass = String.class, example = "xxx@qq.com")
    @PostMapping("/sentMailLoginAuthCode")
    public RespBean<String> sentMailLoginAuthCode(String mail) {
        boolean judge = userService.sentMailLoginAuthCode(mail);
        if (judge) {
            return RespBean.ok("验证码发送成功");
        } else {
            return RespBean.error("该邮箱未注册，请检查邮箱是否正确");
        }
    }

    @ApiOperation("获取当前登录用户信息")
    @GetMapping("/getLoginUserInfo")
    public RespBean<User> getLoginUserInfo() {
        User user = userService.getUserById(LoginUser.getUser().getUserId());
        if (user == null) {
            return RespBean.error("用户未登录");
        }
        user.setUserPassword(null);
        return RespBean.ok(user);
    }


    @ApiOperation(value = "根据用户id查询用户")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataTypeClass = Integer.class, example = "1")
    @GetMapping("/getUserById")
    public RespBean<User> getUserById(Integer userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return RespBean.error("该用户不存在");
        }
        return RespBean.ok(user);
    }

    @ApiOperation(value = "修改用户头像")
    @PostMapping("/uploadUserPhoto")
    public RespBean<String> uploadUserPhoto(@RequestPart MultipartFile photo) {
        return userService.uploadUserPhoto(photo);
    }


    @ApiOperation("更新用户信息")
    @PutMapping("/updateUserInfo")
    public RespBean<String> updateUserInfo(UpdateUserDTO updateUserDTO) {
        boolean updated = userService.updateUserInfo(updateUserDTO);
        if (updated) {
            return RespBean.ok("更新成功");
        } else {
            return RespBean.error("更新失败");
        }
    }


}
