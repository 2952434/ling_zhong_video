package com.lingzhong.video.controller;

import com.lingzhong.video.bean.dto.VideoPublishDTO;
import com.lingzhong.video.bean.vo.RespBean;
import com.lingzhong.video.bean.vo.VideoVo;
import com.lingzhong.video.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
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

    @Resource
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


    @ApiOperation("根据视频ID删除视频")
    @ApiImplicitParam(name = "videoId", value = "视频ID", required = true, dataTypeClass = Integer.class)
    @DeleteMapping("deleteVideoById/{videoId}")
    public RespBean<String> deleteVideoById(@PathVariable Integer videoId) {
        return videoService.deleteVideoById(videoId);
    }

    @Deprecated
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

    @ApiOperation(value = "根据用户IP获取视频")
    @ApiImplicitParam(name = "userIp", value = "用户IP", required = true, dataTypeClass = String.class, example = "192.168.001")
    @GetMapping("/getVideoByIp/{userIp}")
    public RespBean<List<VideoVo>> getVideoByIp(@PathVariable String userIp) {
        List<VideoVo> videoVos = videoService.getVideoByIp(userIp);
        if (videoVos.size() == 0) {
            return RespBean.error("视频到底了");
        }
        return RespBean.ok(videoVos);
    }


    @ApiOperation("登录后根据用户行为推荐视频")
    @ApiImplicitParam(name = "count", value = "推荐视频的条数", required = true, dataTypeClass = Integer.class, example = "10")
    @GetMapping("recommendVideo/{count}")
    public RespBean<List<VideoVo>> recommendVideo(@PathVariable Integer count) {
        List<VideoVo> videoVos = videoService.recommendVideo(count);
        return RespBean.ok(videoVos);
    }


    @ApiOperation(value = "根据用户ID分页查询视频")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataTypeClass = Integer.class, example = "1"),
            @ApiImplicitParam(name = "page", value = "页数，默认从0开始", required = true, dataTypeClass = Integer.class, example = "0"),
            @ApiImplicitParam(name = "count", value = "每页视频条数", required = true, dataTypeClass = Integer.class, example = "20"),
    })
    @GetMapping("getUserVideoByUserId")
    public RespBean<List<VideoVo>> getUserVideoByUserId(Integer userId, Integer page, Integer count) {
        List<VideoVo> userVideoByUserId = videoService.getUserVideoByUserId(userId, page, count);
        if (userVideoByUserId.size() == 0) {
            return RespBean.error("到底了");
        } else {
            return RespBean.ok(userVideoByUserId);
        }

    }

    @ApiOperation(value = "根据输入内容高亮搜索视频")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "content", value = "查询的内容", required = true, dataTypeClass = String.class, example = "视频"),
            @ApiImplicitParam(name = "page", value = "页数，默认从0开始", required = true, dataTypeClass = Integer.class, example = "0"),
            @ApiImplicitParam(name = "count", value = "每页视频条数", required = true, dataTypeClass = Integer.class, example = "10"),
    })
    @GetMapping("/getVideoByEsAndHighLight/{content}/{page}/{count}")
    public RespBean<List<VideoVo>> getVideoByEsAndHighLight(@PathVariable String content, @PathVariable Integer page, @PathVariable Integer count) {
        List<VideoVo> videoByEsAndHighLight = videoService.getVideoByEsAndHighLight(content, page, count);
        if (videoByEsAndHighLight.size() == 0) {
            return RespBean.error("没有搜索到内容，换个搜索词试试");
        } else {
            return RespBean.ok(videoByEsAndHighLight);
        }
    }


    @ApiOperation(value = "根据视频ID获取视频信息")
    @ApiImplicitParam(name = "videoId", value = "视频ID", required = true, dataTypeClass = Integer.class)
    @GetMapping("/getVideoVoByVideoId")
    public RespBean<VideoVo> getVideoVoByVideoId(Integer videoId) {
        return RespBean.ok(videoService.getVideoVoByVideoId(videoId));
    }


}
