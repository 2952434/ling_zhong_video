package com.lingzhong.video.controller;

import com.lingzhong.video.bean.vo.AttentionUserVo;
import com.lingzhong.video.bean.vo.RespBean;
import com.lingzhong.video.service.UserAttentionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 李君祥
 * @Date: 2023/10/30 20:53
 * @Description: 用户关注接口
 */
@RestController
@RequestMapping("/attention")
@Api(tags = "用户关注操作类")
public class UserAttentionController {


    @Resource
    private UserAttentionService userAttentionService;

    @ApiOperation(value = "用户关注接口")
    @ApiImplicitParam(name = "beUserId", value = "被关注的用户id", required = true, dataTypeClass = Integer.class, example = "1")
    @PostMapping("/attentionUserByUserId")
    public RespBean<String> attentionUserByUserId(Integer beUserId) {
        boolean b = userAttentionService.attentionUserByUserId(beUserId);
        if (b) {
            return RespBean.ok("关注成功");
        } else {
            return RespBean.error("关注失败");
        }
    }


    @ApiOperation(value = "根据用户ID取消对该用户的关注")
    @ApiImplicitParam(name = "beUserId", value = "被取消关注的用户id", required = true, dataTypeClass = Integer.class, example = "1")
    @DeleteMapping("/deleteAttentionByUserId")
    public RespBean<String> deleteAttentionByUserId(Integer beUserId) {
        boolean deleted = userAttentionService.deleteAttentionByUserId(beUserId);
        if (deleted) {
            return RespBean.ok("取消关注成功");
        } else {
            return RespBean.error("取消关注失败");
        }
    }


    @ApiOperation("获取关注我的人的列表")
    @ApiImplicitParam(name = "page", value = "页数从0开始", required = true, dataTypeClass = Integer.class, example = "0")
    @GetMapping("/getAttentionMyPeople")
    public RespBean<List<AttentionUserVo>> getAttentionMyPeople(Integer page) {
        List<AttentionUserVo> attentionMyPeople = userAttentionService.getAttentionMyPeople(page);
        if (attentionMyPeople.size() > 0) {
            return RespBean.ok(attentionMyPeople);
        } else {
            return RespBean.error("没有更多数据了");
        }
    }


    @ApiOperation("获取我关注的人的列表")
    @ApiImplicitParam(name = "page", value = "页数从0开始", required = true, dataTypeClass = Integer.class, example = "0")
    @GetMapping("/getMyAttentionPeople")
    public RespBean<List<AttentionUserVo>> getMyAttentionPeople(Integer page) {
        List<AttentionUserVo> attentionMyPeople = userAttentionService.getMyAttentionPeople(page);
        if (attentionMyPeople.size() > 0) {
            return RespBean.ok(attentionMyPeople);
        } else {
            return RespBean.error("没有更多数据了");
        }
    }


    @ApiOperation("获取粉丝数量")
    @ApiImplicitParam(name = "userId", value = "用户ID如果不传为当前用户ID", dataTypeClass = Integer.class)
    @GetMapping("/getAttentionMyCount")
    public RespBean<Integer> getAttentionMyCount(Integer userId) {
        Integer attentionMyCount = userAttentionService.getAttentionMyCount(userId);
        if (attentionMyCount == null) {
            return RespBean.ok(0);
        } else {
            return RespBean.ok(attentionMyCount);
        }
    }


    @ApiOperation("获取关注的人的数量")
    @ApiImplicitParam(name = "userId", value = "用户ID如果不传为当前用户ID", dataTypeClass = Integer.class)
    @GetMapping("/getMyAttentionCount")
    public RespBean<Integer> getMyAttentionCount(Integer userId) {
        Integer myAttentionCount = userAttentionService.getMyAttentionCount(userId);
        if (myAttentionCount == null) {
            return RespBean.ok(0);
        } else {
            return RespBean.ok(myAttentionCount);
        }
    }

    @ApiOperation(value = "判断登录用户是否关注该用户")
    @ApiImplicitParam(name = "beUserId", value = "判断我是否关注他的Id", dataTypeClass = Integer.class)
    @GetMapping("/judgeMyIsAttention")
    public RespBean<Boolean> judgeMyIsAttention(Integer beUserId) {
        boolean myIsAttention = userAttentionService.judgeMyIsAttention(beUserId);
        if (myIsAttention) {
            return RespBean.ok("已关注该用户", true);
        }
        return RespBean.ok("未关注该用户", false);
    }


}
