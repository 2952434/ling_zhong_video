package com.lingzhong.video.controller;

import com.lingzhong.video.bean.dto.VideoPublishDTO;
import com.lingzhong.video.bean.vo.RespBean;
import com.lingzhong.video.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * @Author: 李君祥
 * @Date: 2023/10/28 17:03
 * @Description: 视频控制类
 */
@RestController
@Api(tags = "视频操作类")
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @ApiOperation(value = "视频上传接口")
    @PostMapping("/uploadVideo")
    public RespBean uploadVideo(VideoPublishDTO videoPublishDTO, @RequestPart MultipartFile file) throws Exception {
        Boolean aBoolean = videoService.uploadVideo(file, videoPublishDTO);
        if (aBoolean) {
            return RespBean.ok("上传视频成功");
        } else {
            return RespBean.error("上传视频失败，请重新上传");
        }
    }




}
