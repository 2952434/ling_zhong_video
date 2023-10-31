package com.lingzhong.video.bean.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;


@TableName(value ="video_collect")
@Data
public class VideoCollect implements Serializable {
    /**
     * 收藏主键
     */
    @TableId
    private Integer collectId;

    /**
     * 收藏的视频id
     */
    private Integer videoId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 被收藏的用户id（用于收藏消息提醒）
     */
    private Integer beUserId;

    /**
     * 收藏时间
     */
    private Date collectDate;

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
        VideoCollect other = (VideoCollect) that;
        return (this.getCollectId() == null ? other.getCollectId() == null : this.getCollectId().equals(other.getCollectId()))
            && (this.getVideoId() == null ? other.getVideoId() == null : this.getVideoId().equals(other.getVideoId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getBeUserId() == null ? other.getBeUserId() == null : this.getBeUserId().equals(other.getBeUserId()))
            && (this.getCollectDate() == null ? other.getCollectDate() == null : this.getCollectDate().equals(other.getCollectDate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCollectId() == null) ? 0 : getCollectId().hashCode());
        result = prime * result + ((getVideoId() == null) ? 0 : getVideoId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getBeUserId() == null) ? 0 : getBeUserId().hashCode());
        result = prime * result + ((getCollectDate() == null) ? 0 : getCollectDate().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", collectId=").append(collectId);
        sb.append(", videoId=").append(videoId);
        sb.append(", userId=").append(userId);
        sb.append(", beUserId=").append(beUserId);
        sb.append(", collectDate=").append(collectDate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}