package com.lingzhong.video.bean.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: 李君祥
 * @Date: 2023/10/28 17:22
 * @Description: 用户发表视频时填入的信息
 */
@Data
@ApiModel("视频上传参数")
public class VideoPublishDTO {

    /**
     * 视频发表的地理位置
     */
    @ApiModelProperty(value = "视频发表用户的地址", example = "北京")
    private String videoAddress;

    @ApiModelProperty(value = "视频描述", example = "这个视频很有趣，分享给大家")
    private String videoDescription;

    /**
     * (0：公开，1：私有)
     */
    @ApiModelProperty(value = "视频是否公开", example = "0")
    private Integer videoPrivate;


    /**
     * 视频绑定的标签id集合
     */
    @ApiModelProperty(value = "视频绑定的标签id集合", example = "1,2")
    private List<Integer> labelIds;

}
