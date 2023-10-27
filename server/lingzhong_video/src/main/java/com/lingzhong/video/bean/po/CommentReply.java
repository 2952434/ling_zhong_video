package com.lingzhong.video.bean.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName comment_reply
 */
@TableName(value ="comment_reply")
@Data
public class CommentReply implements Serializable {
    /**
     * 主键id
     */
    @TableId
    private Integer id;

    /**
     * 如果是评论为视频id，如果是回复是评论id
     */
    private Integer videoOrCommentId;

    /**
     * 发表回复的用户id
     */
    private Integer userId;

    /**
     * 被回复或评论的用户id
     */
    private Integer beUserId;

    /**
     * 0：评论，1：回复
     */
    private Integer commentOrReply;

    /**
     * 回复的内容
     */
    private String replyContent;

    /**
     * 发表时间
     */
    private Date replyDate;

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
        CommentReply other = (CommentReply) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getVideoOrCommentId() == null ? other.getVideoOrCommentId() == null : this.getVideoOrCommentId().equals(other.getVideoOrCommentId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getBeUserId() == null ? other.getBeUserId() == null : this.getBeUserId().equals(other.getBeUserId()))
            && (this.getCommentOrReply() == null ? other.getCommentOrReply() == null : this.getCommentOrReply().equals(other.getCommentOrReply()))
            && (this.getReplyContent() == null ? other.getReplyContent() == null : this.getReplyContent().equals(other.getReplyContent()))
            && (this.getReplyDate() == null ? other.getReplyDate() == null : this.getReplyDate().equals(other.getReplyDate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getVideoOrCommentId() == null) ? 0 : getVideoOrCommentId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getBeUserId() == null) ? 0 : getBeUserId().hashCode());
        result = prime * result + ((getCommentOrReply() == null) ? 0 : getCommentOrReply().hashCode());
        result = prime * result + ((getReplyContent() == null) ? 0 : getReplyContent().hashCode());
        result = prime * result + ((getReplyDate() == null) ? 0 : getReplyDate().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", videoOrCommentId=").append(videoOrCommentId);
        sb.append(", userId=").append(userId);
        sb.append(", beUserId=").append(beUserId);
        sb.append(", commentOrReply=").append(commentOrReply);
        sb.append(", replyContent=").append(replyContent);
        sb.append(", replyDate=").append(replyDate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}