package com.lingzhong.video.bean.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@TableName(value ="video_like")
@Data
public class VideoLike implements Serializable {
    /**
     * 点赞主键id
     */
    @TableId
    private Integer likeId;

    /**
     * 点赞的视频id
     */
    @ApiModelProperty(name = "视频id" , dataType = "Integer" , required = true)
    private Integer videoId;

    /**
     * 点赞的用户id
     */
    private Integer userId;

    /**
     * 被点赞用户的id(用于消息提醒)
     */
    @ApiModelProperty(name = "被点赞用户的id" , dataType = "Integer" , required = true)
    private Integer beUserId;

    /**
     * 点赞时间（用于排序）
     */
    @ApiModelProperty(name = "点赞时间" , dataType = "Date" , required = true)
    private Date likeDate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        VideoLike other = (VideoLike) that;
        return (this.getLikeId() == null ? other.getLikeId() == null : this.getLikeId().equals(other.getLikeId()))
            && (this.getVideoId() == null ? other.getVideoId() == null : this.getVideoId().equals(other.getVideoId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getBeUserId() == null ? other.getBeUserId() == null : this.getBeUserId().equals(other.getBeUserId()))
            && (this.getLikeDate() == null ? other.getLikeDate() == null : this.getLikeDate().equals(other.getLikeDate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getLikeId() == null) ? 0 : getLikeId().hashCode());
        result = prime * result + ((getVideoId() == null) ? 0 : getVideoId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getBeUserId() == null) ? 0 : getBeUserId().hashCode());
        result = prime * result + ((getLikeDate() == null) ? 0 : getLikeDate().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", likeId=").append(likeId);
        sb.append(", videoId=").append(videoId);
        sb.append(", userId=").append(userId);
        sb.append(", beUserId=").append(beUserId);
        sb.append(", likeDate=").append(likeDate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}