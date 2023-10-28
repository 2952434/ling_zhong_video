package com.lingzhong.video.controller;

import com.lingzhong.video.bean.dto.VideoPublishDTO;
import com.lingzhong.video.bean.vo.RespBean;
import com.lingzhong.video.bean.vo.VideoVo;
import com.lingzhong.video.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


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
    public RespBean<String> uploadVideo(VideoPublishDTO videoPublishDTO, @RequestPart MultipartFile file) throws Exception {
        Boolean aBoolean = videoService.uploadVideo(file, videoPublishDTO);
        if (aBoolean) {
            return RespBean.ok("上传视频成功");
        } else {
            return RespBean.error("上传视频失败，请重新上传");
        }
    }


    @ApiOperation(value = "分页查询视频")
    @ApiImplicitParam(name = "page", value = "页数，默认从0开始，一页10条数据", required = true, dataTypeClass = Integer.class, example = "0")
    @GetMapping("getVideo/{page}")
    public RespBean<List<VideoVo>> getVideo(@PathVariable Integer page) {
        List<VideoVo> video = videoService.getVideo(page);
        if (video == null) {
            return RespBean.error("视频查询失败");
        } else {
            return RespBean.ok(video);
        }
    }


}
