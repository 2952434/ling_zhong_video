package com.lingzhong.video.bean.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;


@TableName(value = "video")
@Data
public class Video implements Serializable {
    /**
     * 视频id
     */
    @TableId(type = IdType.AUTO)
    private Integer videoId;

    /**
     * 视频名
     */
    private String videoName;

    /**
     * 视频url地址
     */
    private String videoUrl;

    /**
     * 发视频用户id
     */
    private Integer videoUserId;

    /**
     * 视频发表的地理位置
     */
    private String videoAddress;

    /**
     * (0：公开，1：私有)
     */
    private Integer videoPrivate;

    /**
     * 发表时间
     */
    private Date videoDate;

}