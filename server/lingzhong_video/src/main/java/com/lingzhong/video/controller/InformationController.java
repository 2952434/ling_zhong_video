package com.lingzhong.video.controller;

import com.lingzhong.video.bean.po.*;
import com.lingzhong.video.bean.vo.RespBean;
import com.lingzhong.video.service.InformationService;
import com.lingzhong.video.utils.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author 孙铭杰
 * @since 2023-10-31 11:25
 */
@RestController
@RequestMapping("/lingzhong/information")
@Api(tags = "消息操作接口")
public class InformationController {

    private InformationService informationService;

    public InformationController(InformationService informationService) {
        this.informationService = informationService;
    }

    @RequestMapping(value = "/video/like" , method = RequestMethod.GET)
    @ApiOperation("获取视频点赞消息")
    public RespBean<List<VideoLike>>  getVideoLikeInformation(){
        User user = LoginUser.getUser();
        if (user == null){
            return RespBean.error("无法操作");
        }
        Integer userId = user.getUserId();
        List<VideoLike> videoLikeInformation = informationService.getVideoLikeInformation(userId);
        return RespBean.ok(videoLikeInformation);
    }

    @RequestMapping(value = "/video/collect",method = RequestMethod.GET)
    @ApiOperation("获取视频收藏消息")
    public RespBean<List<VideoCollect>>  getVideoCollectInformation(){
        User user = LoginUser.getUser();
        if (user == null){
            return RespBean.error("无法操作");
        }
        Integer userId = user.getUserId();
        List<VideoCollect> videoCollectList = informationService.getVideoCollectInformation(userId);
        return RespBean.ok(videoCollectList);
    }

    @RequestMapping(value = "/comment/like",method = RequestMethod.GET)
    @ApiOperation("获取评论点赞消息")
    public RespBean<List<CommentLike>>  getCommentLikeInformation(){
        User user = LoginUser.getUser();
        if (user == null){
            return RespBean.error("无法操作");
        }
        Integer userId = user.getUserId();
        List<CommentLike> commentLikeList = informationService.getCommentLikeInformation(userId);
        return RespBean.ok(commentLikeList);
    }

    @RequestMapping(value = "/comment",method = RequestMethod.GET)
    @ApiOperation("获取被回复的消息")
    public RespBean<List<CommentReply>> getCommentInformation(){
        User user = LoginUser.getUser();
        if (user == null){
            return RespBean.error("无法操作");
        }
        Integer userId = user.getUserId();
        List<CommentReply> commentInformation = informationService.getCommentInformation(userId);
        return RespBean.ok(commentInformation);
    }


    @RequestMapping(value = "/video/comment",method = RequestMethod.GET)
    @ApiOperation("获取视频被评论的消息")
    public RespBean<List<CommentReply>> getVideoCommentInformation(){
        User user = LoginUser.getUser();
        if (user == null){
            return RespBean.error("无法操作");
        }
        Integer userId = user.getUserId();
        List<CommentReply> videoCommentInformation = informationService.getVideoCommentInformation(userId);
        return RespBean.ok(videoCommentInformation);
    }

}
