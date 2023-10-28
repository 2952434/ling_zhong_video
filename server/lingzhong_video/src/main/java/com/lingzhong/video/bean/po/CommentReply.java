package com.lingzhong.video.bean.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotNull;


@TableName(value ="comment_reply")
@Data
@ApiModel
public class CommentReply implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 评论id
     */
    private Long commentId;

    /**
     * 视频id
     */
    @NotNull(message = "视频id不能为空")
    @ApiParam(name = "视频id",type = "Integer",required = true)
    private Integer videoId;

    /**
     * 发表评论的用户id
     */
    @NotNull(message = "用户id不能为空")
    @ApiParam(name = "用户id",type = "Integer",required = true)
    private Integer userId;

    /**
     * 评论内容
     */
    @NotNull(message = "评论内容不能为空")
    @ApiParam(name = "评论内容",type = "String",required = true)
    private String commentTxt;

    /**
     * 评论点赞数
     */
    private Integer commentLike;

    /**
     * 父评论id，-1代表评论，其他代表回复
     */
    private Integer commentFid;

    /**
     * 发表时间
     */
    @NotNull(message = "发表时间不能为空")
    @ApiParam(name = "发表时间",type = "Date",required = true)
    private Date replyDate;

    /**
     * 子评论
     */
    @TableField(exist = false)
    private List<CommentReply> sonCommentReplyList;

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
            && (this.getCommentId() == null ? other.getCommentId() == null : this.getCommentId().equals(other.getCommentId()))
            && (this.getVideoId() == null ? other.getVideoId() == null : this.getVideoId().equals(other.getVideoId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getCommentTxt() == null ? other.getCommentTxt() == null : this.getCommentTxt().equals(other.getCommentTxt()))
            && (this.getCommentLike() == null ? other.getCommentLike() == null : this.getCommentLike().equals(other.getCommentLike()))
            && (this.getCommentFid() == null ? other.getCommentFid() == null : this.getCommentFid().equals(other.getCommentFid()))
            && (this.getReplyDate() == null ? other.getReplyDate() == null : this.getReplyDate().equals(other.getReplyDate()))
            && (this.getSonCommentReplyList() == null ? other.getSonCommentReplyList() == null : this.getSonCommentReplyList().equals(other.getSonCommentReplyList()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCommentId() == null) ? 0 : getCommentId().hashCode());
        result = prime * result + ((getVideoId() == null) ? 0 : getVideoId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getCommentTxt() == null) ? 0 : getCommentTxt().hashCode());
        result = prime * result + ((getCommentLike() == null) ? 0 : getCommentLike().hashCode());
        result = prime * result + ((getCommentFid() == null) ? 0 : getCommentFid().hashCode());
        result = prime * result + ((getReplyDate() == null) ? 0 : getReplyDate().hashCode());
        result = prime * result + ((getSonCommentReplyList() == null) ? 0 : getSonCommentReplyList().hashCode());
        return result;
    }

}