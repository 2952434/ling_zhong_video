package com.lingzhong.video.controller;


import com.lingzhong.video.bean.po.User;
import com.lingzhong.video.bean.po.Video;
import com.lingzhong.video.bean.po.VideoCollect;
import com.lingzhong.video.bean.po.VideoLike;
import com.lingzhong.video.bean.vo.RespBean;
import com.lingzhong.video.bean.vo.VideoVo;
import com.lingzhong.video.service.*;
import com.lingzhong.video.utils.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@Api(tags = "视频相关数据操作接口")
@RequestMapping("/lingzhong/video/data")
public class VideoRelevantDataController {

    private VideoLikeService videoLikeService;

    private VideoDataService videoDataService;

    private VideoCollectService videoCollectService;

    private VideoService videoService;

    private InformationService informationService;

    private static final Integer ADD_NUM = 1;
    private static final Integer SUBTRACT_NUM = -1;

    public VideoRelevantDataController(VideoLikeService videoLikeService, VideoDataService videoDataService,
                                       VideoCollectService videoCollectService, VideoService videoService, InformationService informationService) {
        this.videoLikeService = videoLikeService;
        this.videoDataService = videoDataService;
        this.videoCollectService = videoCollectService;
        this.videoService = videoService;
        this.informationService = informationService;
    }

    @RequestMapping(value = "/collect/add" , method = RequestMethod.POST)
    @ApiOperation(value = "收藏视频")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "videoId" , value = "视频id" , required = true , dataTypeClass = Integer.class , example = "1"),
            @ApiImplicitParam(name = "beUserId" , value = "被收藏视频的用户id" , required = true , dataTypeClass = Integer.class , example = "1")
    })
    public RespBean<Integer> addCollectVideo(@RequestParam("videoId") Integer videoId ,
                                             @RequestParam("beUserId") Integer beUserId){
        User user = LoginUser.getUser();
        if (user == null)
            return RespBean.error("无法操作");
        Integer userId = user.getUserId();
        VideoCollect videoCollect = new VideoCollect();
        videoCollect.setVideoId(videoId);
        videoCollect.setBeUserId(beUserId);
        videoCollect.setUserId(userId);
        videoCollect.setCollectDate(new Date());

        Integer addNewVideoCollectDataStatus = videoCollectService.addNewVideoCollectData(videoCollect);
        Integer updateVideoCollectNumStatus = videoDataService.updateVideoCollectNum(videoCollect.getVideoId(), ADD_NUM);
        /**
         * 通知被收藏用户
         */
        informationService.innerNewCollectInformation(videoCollect);

        return RespBean.ok(addNewVideoCollectDataStatus);
    }

    @RequestMapping(value = "/collect/cancel" , method = RequestMethod.DELETE)
    @ApiOperation(value = "取消收藏")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "videoId" , value = "视频id" , required = true , dataTypeClass = Integer.class , example = "1"),
            @ApiImplicitParam(name = "beUserId" , value = "被收藏视频的用户id" , required = true , dataTypeClass = Integer.class , example = "1")
    })
    public RespBean<Integer> cancelCollectVideo(@RequestParam("videoId") Integer videoId ,
                                                      @RequestParam("beUserId") Integer beUserId){
        User user = LoginUser.getUser();
        if (user == null)
            return RespBean.error("无法操作");
        Integer userId = user.getUserId();
        Integer delVideoCollectDataStatus = videoCollectService.delVideoCollectData(videoId, userId, beUserId);
        Integer updateVideoCollectNumStatus = videoDataService.updateVideoCollectNum(videoId, SUBTRACT_NUM);
        return RespBean.ok(delVideoCollectDataStatus);
    }

    @RequestMapping(value = "/like/add" , method = RequestMethod.POST)
    @ApiOperation(value = "喜欢视频")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "videoId" , value = "视频id" , required = true , dataTypeClass = Integer.class , example = "1"),
            @ApiImplicitParam(name = "beUserId" , value = "被收藏视频的用户id" , required = true , dataTypeClass = Integer.class , example = "1")
    })
    public RespBean<Integer> addLikeVideo(@RequestParam("videoId") Integer videoId ,@RequestParam("beUserId") Integer beUserId){
        User user = LoginUser.getUser();
        if (user == null)
            return RespBean.error("无法操作");
        Integer userId = user.getUserId();
        VideoLike videoLike = new VideoLike();
        videoLike.setVideoId(videoId);
        videoLike.setBeUserId(beUserId);
        videoLike.setUserId(userId);
        videoLike.setLikeDate(new Date());
        Integer addNewVideoLikeDataStatus = videoLikeService.addNewVideoLikeData(videoLike);
        Integer updateVideoLikeNumStatus = videoDataService.updateVideoLikeNum(videoLike.getVideoId(), ADD_NUM);
        /**
         * 通知被喜欢用户
         */
        informationService.innerNewLikeInformation(videoLike);

        return RespBean.ok(addNewVideoLikeDataStatus);
    }

    @RequestMapping(value = "/like/cancel" , method = RequestMethod.DELETE)
    @ApiOperation(value = "取消喜欢")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "videoId" , value = "视频id" , required = true , dataTypeClass = Integer.class , example = "1"),
            @ApiImplicitParam(name = "beUserId" , value = "被喜欢视频的用户id" , required = true , dataTypeClass = Integer.class , example = "1")
    })
    public RespBean<Integer> cancelLikeVideo(@RequestParam("videoId") Integer videoId ,
                                                      @RequestParam("beUserId") Integer beUserId){
        User user = LoginUser.getUser();
        if (user == null)
            return RespBean.error("无法操作");
        Integer userId = user.getUserId();
        Integer delVideoLikeDataStatus = videoLikeService.delVideoLikeData(videoId, userId, beUserId);
        Integer updateVideoLikeNumStatus = videoDataService.updateVideoCollectNum(videoId, SUBTRACT_NUM);
        return RespBean.ok(delVideoLikeDataStatus);
    }


    @RequestMapping(value = "/like/user" , method = RequestMethod.GET)
    @ApiOperation(value = "查看用户喜欢的视频")
    public RespBean<List<VideoVo>> checkUserLike(){
        User user = LoginUser.getUser();
        if (user == null)
            return RespBean.error("无法操作");
        Integer userId = user.getUserId();
        List<VideoVo> videoList = videoService.getUserLikeVideoList(userId);
        return RespBean.ok(videoList);
    }

    @RequestMapping(value = "/collect/user" , method = RequestMethod.GET)
    @ApiOperation(value = "查看用户收藏的视频")
    public RespBean<List<VideoVo>> checkUserCollect(){
        User user = LoginUser.getUser();
        if (user == null)
            return RespBean.error("无法操作");
        Integer userId = user.getUserId();
        List<VideoVo> videoList = videoService.getUserCollectVideoList(userId);
        return RespBean.ok(videoList);
    }

    @RequestMapping(value = "/check/like/collect" , method = RequestMethod.GET)
    @ApiOperation(value = "查看用户是否喜欢了该视频(0代表未喜欢也未收藏，1代表喜欢，2代表收藏，3代表收藏且喜欢)")
    public RespBean<Integer> checkUserLikeOrCollect(Integer videoId){
        User user = LoginUser.getUser();
        if (user == null)
            return RespBean.error("未登录");
        Integer userId = user.getUserId();
        Integer isLike = videoLikeService.selectByUserIdAndVideoId(userId, videoId) == null ? 0 : 1;
        Integer isCollect = videoCollectService.selectByUserIdAndVideoId(userId, videoId) == null ? 0 : 1;
        return RespBean.ok(isLike + isCollect);
    }

}
