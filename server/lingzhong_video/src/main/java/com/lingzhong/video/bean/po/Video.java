package com.lingzhong.video.bean.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;


@TableName(value ="video")
@Data
public class Video implements Serializable {
    /**
     * 视频id
     */
    @TableId
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
        Video other = (Video) that;
        return (this.getVideoId() == null ? other.getVideoId() == null : this.getVideoId().equals(other.getVideoId()))
            && (this.getVideoName() == null ? other.getVideoName() == null : this.getVideoName().equals(other.getVideoName()))
            && (this.getVideoUrl() == null ? other.getVideoUrl() == null : this.getVideoUrl().equals(other.getVideoUrl()))
            && (this.getVideoUserId() == null ? other.getVideoUserId() == null : this.getVideoUserId().equals(other.getVideoUserId()))
            && (this.getVideoAddress() == null ? other.getVideoAddress() == null : this.getVideoAddress().equals(other.getVideoAddress()))
            && (this.getVideoPrivate() == null ? other.getVideoPrivate() == null : this.getVideoPrivate().equals(other.getVideoPrivate()))
            && (this.getVideoDate() == null ? other.getVideoDate() == null : this.getVideoDate().equals(other.getVideoDate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getVideoId() == null) ? 0 : getVideoId().hashCode());
        result = prime * result + ((getVideoName() == null) ? 0 : getVideoName().hashCode());
        result = prime * result + ((getVideoUrl() == null) ? 0 : getVideoUrl().hashCode());
        result = prime * result + ((getVideoUserId() == null) ? 0 : getVideoUserId().hashCode());
        result = prime * result + ((getVideoAddress() == null) ? 0 : getVideoAddress().hashCode());
        result = prime * result + ((getVideoPrivate() == null) ? 0 : getVideoPrivate().hashCode());
        result = prime * result + ((getVideoDate() == null) ? 0 : getVideoDate().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", videoId=").append(videoId);
        sb.append(", videoName=").append(videoName);
        sb.append(", videoUrl=").append(videoUrl);
        sb.append(", videoUserId=").append(videoUserId);
        sb.append(", videoAddress=").append(videoAddress);
        sb.append(", videoPrivate=").append(videoPrivate);
        sb.append(", videoDate=").append(videoDate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}