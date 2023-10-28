package com.lingzhong.video.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author: 李君祥
 * @Date: 2023/10/28 21:44
 * @Description: 返回前端的视频信息
 */

@Data
@ApiModel(value = "视频展示类")
public class VideoVo {

    /**
     * 视频id
     */
    @ApiModelProperty(value = "视频id")
    private Integer videoId;

    /**
     * 视频描述
     */
    @ApiModelProperty(value = "视频描述")
    private String videoDescription;

    /**
     * 视频url地址
     */
    @ApiModelProperty(value = "视频url地址")
    private String videoUrl;

    /**
     * 发视频用户id
     */
    @ApiModelProperty(value = "发视频用户id")
    private Integer videoUserId;

    /**
     * 视频发表的地理位置
     */
    @ApiModelProperty(value = "视频发表的地理位置")
    private String videoAddress;

    /**
     * 发表时间
     */
    @ApiModelProperty(value = "发表时间")
    private String videoDate;

    /**
     * 视频点赞量
     */
    @ApiModelProperty(value = "视频点赞量")
    private Integer videoLikeNum;

    /**
     * 视频收藏量
     */
    @ApiModelProperty(value = "视频收藏量")
    private Integer videoCollectNum;

    /**
     * 视频评论量
     */
    @ApiModelProperty(value = "视频评论量")
    private Integer videoCommentNum;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String userName;

    /**
     * 用户头像
     */
    @ApiModelProperty(value = "用户头像")
    private String userPhoto;


}
