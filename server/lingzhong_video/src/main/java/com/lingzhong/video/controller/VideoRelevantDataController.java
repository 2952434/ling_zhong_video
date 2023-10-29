package com.lingzhong.video.controller;


import com.lingzhong.video.bean.po.Video;
import com.lingzhong.video.bean.po.VideoCollect;
import com.lingzhong.video.bean.po.VideoLike;
import com.lingzhong.video.bean.vo.VideoVo;
import com.lingzhong.video.service.VideoCollectService;
import com.lingzhong.video.service.VideoDataService;
import com.lingzhong.video.service.VideoLikeService;
import com.lingzhong.video.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "视频相关数据操作接口")
@RequestMapping("/lingzhong/video/data")
public class VideoRelevantDataController {

    private VideoLikeService videoLikeService;

    private VideoDataService videoDataService;

    private VideoCollectService videoCollectService;

    private VideoService videoService;

    private static final Integer ADD_NUM = 1;
    private static final Integer SUBTRACT_NUM = -1;

    public VideoRelevantDataController(VideoLikeService videoLikeService, VideoDataService videoDataService,
                                       VideoCollectService videoCollectService, VideoService videoService) {
        this.videoLikeService = videoLikeService;
        this.videoDataService = videoDataService;
        this.videoCollectService = videoCollectService;
        this.videoService = videoService;
    }

    @RequestMapping(value = "/collect/add" , method = RequestMethod.PUT)
    @ApiOperation(value = "收藏视频")
    @ApiImplicitParam(name = "videoCollect", value = "收藏所需信息", required = true, dataTypeClass = VideoCollect.class, example = "")
    public ResponseEntity<Integer> addCollectVideo(@RequestBody VideoCollect videoCollect){
        Integer addNewVideoCollectDataStatus = videoCollectService.addNewVideoCollectData(videoCollect);
        Integer updateVideoCollectNumStatus = videoDataService.updateVideoCollectNum(videoCollect.getVideoId(), ADD_NUM);
        /**
         * 通知被收藏用户
         */


        return new ResponseEntity<Integer>(addNewVideoCollectDataStatus , HttpStatus.OK);
    }

    @RequestMapping(value = "/collect/cancel" , method = RequestMethod.POST)
    @ApiOperation(value = "取消收藏")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "videoId" , value = "视频id" , required = true , dataTypeClass = Integer.class , example = "1"),
            @ApiImplicitParam(name = "userId" , value = "用户id" , required = true , dataTypeClass = Integer.class , example = "1"),
            @ApiImplicitParam(name = "beUserId" , value = "被收藏视频的用户id" , required = true , dataTypeClass = Integer.class , example = "1")
    })
    public ResponseEntity<Integer> cancelCollectVideo(@RequestParam("videoId") Integer videoId ,
                                                      @RequestParam("userId") Integer userId ,
                                                      @RequestParam("beUserId") Integer beUserId){
        Integer delVideoCollectDataStatus = videoCollectService.delVideoCollectData(videoId, userId, beUserId);
        Integer updateVideoCollectNumStatus = videoDataService.updateVideoCollectNum(videoId, SUBTRACT_NUM);
        return new ResponseEntity<Integer>(delVideoCollectDataStatus , HttpStatus.OK);
    }

    @RequestMapping(value = "/like/add" , method = RequestMethod.PUT)
    @ApiOperation(value = "喜欢视频")
    @ApiImplicitParam(name = "videoLike", value = "喜欢所需信息", required = true, dataTypeClass = VideoLike.class, example = "")
    public ResponseEntity<Integer> addLikeVideo(@RequestBody VideoLike videoLike){
        Integer addNewVideoLikeDataStatus = videoLikeService.addNewVideoLikeData(videoLike);
        Integer updateVideoLikeNumStatus = videoDataService.updateVideoLikeNum(videoLike.getVideoId(), ADD_NUM);
        /**
         * 通知被喜欢用户
         */


        return new ResponseEntity<Integer>(addNewVideoLikeDataStatus , HttpStatus.OK);
    }

    @RequestMapping(value = "/like/cancel" , method = RequestMethod.POST)
    @ApiOperation(value = "取消喜欢")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "videoId" , value = "视频id" , required = true , dataTypeClass = Integer.class , example = "1"),
            @ApiImplicitParam(name = "userId" , value = "用户id" , required = true , dataTypeClass = Integer.class , example = "1"),
            @ApiImplicitParam(name = "beUserId" , value = "被喜欢视频的用户id" , required = true , dataTypeClass = Integer.class , example = "1")
    })
    public ResponseEntity<Integer> cancelLikeVideo(@RequestParam("videoId") Integer videoId ,
                                                      @RequestParam("userId") Integer userId ,
                                                      @RequestParam("beUserId") Integer beUserId){
        Integer delVideoLikeDataStatus = videoLikeService.delVideoLikeData(videoId, userId, beUserId);
        Integer updateVideoLikeNumStatus = videoDataService.updateVideoCollectNum(videoId, SUBTRACT_NUM);
        return new ResponseEntity<Integer>(delVideoLikeDataStatus , HttpStatus.OK);
    }


    @RequestMapping(value = "/like/user" , method = RequestMethod.GET)
    @ApiOperation(value = "查看用户喜欢的视频")
    @ApiImplicitParam(name = "userId" , value = "用户id" , required = true , dataTypeClass = Integer.class , example = "1")
    public ResponseEntity<List<VideoVo>> checkUserLike(@RequestParam("userId") Integer userId){
        List<VideoVo> videoList = videoService.getUserLikeVideoList(userId);
        return new ResponseEntity<List<VideoVo>>(videoList , HttpStatus.OK);
    }

    @RequestMapping(value = "/collect/user" , method = RequestMethod.GET)
    @ApiOperation(value = "查看用户收藏的视频")
    @ApiImplicitParam(name = "userId" , value = "用户id" , required = true , dataTypeClass = Integer.class , example = "1")
    public ResponseEntity<List<VideoVo>> checkUserCollect(@RequestParam("userId") Integer userId){
        List<VideoVo> videoList = videoService.getUserCollectVideoList(userId);
        return new ResponseEntity<List<VideoVo>>(videoList , HttpStatus.OK);
    }
}
