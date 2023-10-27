package com.lingzhong.video.bean.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName video_data
 */
@TableName(value ="video_data")
@Data
public class VideoData implements Serializable {
    /**
     * 视频id
     */
    @TableId
    private Integer videoId;

    /**
     * 视频点赞量
     */
    private Integer videoLikeNum;

    /**
     * 视频收藏量
     */
    private Integer videoCollectNum;

    /**
     * 视频评论量
     */
    private Integer videoCommentNum;

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
        VideoData other = (VideoData) that;
        return (this.getVideoId() == null ? other.getVideoId() == null : this.getVideoId().equals(other.getVideoId()))
            && (this.getVideoLikeNum() == null ? other.getVideoLikeNum() == null : this.getVideoLikeNum().equals(other.getVideoLikeNum()))
            && (this.getVideoCollectNum() == null ? other.getVideoCollectNum() == null : this.getVideoCollectNum().equals(other.getVideoCollectNum()))
            && (this.getVideoCommentNum() == null ? other.getVideoCommentNum() == null : this.getVideoCommentNum().equals(other.getVideoCommentNum()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getVideoId() == null) ? 0 : getVideoId().hashCode());
        result = prime * result + ((getVideoLikeNum() == null) ? 0 : getVideoLikeNum().hashCode());
        result = prime * result + ((getVideoCollectNum() == null) ? 0 : getVideoCollectNum().hashCode());
        result = prime * result + ((getVideoCommentNum() == null) ? 0 : getVideoCommentNum().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", videoId=").append(videoId);
        sb.append(", videoLikeNum=").append(videoLikeNum);
        sb.append(", videoCollectNum=").append(videoCollectNum);
        sb.append(", videoCommentNum=").append(videoCommentNum);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}