package com.lingzhong.video.controller;


import com.lingzhong.video.bean.po.VideoCollect;
import com.lingzhong.video.bean.po.VideoLike;
import com.lingzhong.video.service.VideoCollectService;
import com.lingzhong.video.service.VideoDataService;
import com.lingzhong.video.service.VideoLikeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "视频相关数据操作接口")
@RequestMapping("/lingzhong/video/data")
public class VideoRelevantDataController {

    private VideoLikeService videoLikeService;

    private VideoDataService videoDataService;

    private VideoCollectService videoCollectService;

    private static final Integer ADD_NUM = 1;
    private static final Integer SUBTRACT_NUM = -1;

    public VideoRelevantDataController(VideoLikeService videoLikeService, VideoDataService videoDataService, VideoCollectService videoCollectService) {
        this.videoLikeService = videoLikeService;
        this.videoDataService = videoDataService;
        this.videoCollectService = videoCollectService;
    }

    @RequestMapping(value = "/collect/add" , method = RequestMethod.PUT)
    @ApiOperation(value = "收藏视频")
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
    public ResponseEntity<Integer> cancelCollectVideo(@RequestParam("videoId") Integer videoId ,
                                                      @RequestParam("userId") Integer userId ,
                                                      @RequestParam("beUserId") Integer beUserId){
        Integer delVideoCollectDataStatus = videoCollectService.delVideoCollectData(videoId, userId, beUserId);
        Integer updateVideoCollectNumStatus = videoDataService.updateVideoCollectNum(videoId, SUBTRACT_NUM);
        return new ResponseEntity<Integer>(delVideoCollectDataStatus , HttpStatus.OK);
    }

    @RequestMapping(value = "/like/add" , method = RequestMethod.PUT)
    @ApiOperation(value = "喜欢视频")
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
    public ResponseEntity<Integer> cancelLikeVideo(@RequestParam("videoId") Integer videoId ,
                                                      @RequestParam("userId") Integer userId ,
                                                      @RequestParam("beUserId") Integer beUserId){
        Integer delVideoLikeDataStatus = videoLikeService.delVideoLikeData(videoId, userId, beUserId);
        Integer updateVideoLikeNumStatus = videoDataService.updateVideoCollectNum(videoId, SUBTRACT_NUM);
        return new ResponseEntity<Integer>(delVideoLikeDataStatus , HttpStatus.OK);
    }
}
