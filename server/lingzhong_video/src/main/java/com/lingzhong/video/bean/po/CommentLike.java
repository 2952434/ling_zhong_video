package com.lingzhong.video.bean.po;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName(value ="comment_like")
@Data
@ApiModel
public class CommentLike implements Serializable {

    /**
     * 用户id
     */
    @ApiParam(name = "用户id",type = "Integer",required = true)
    private Integer userId;
    /**
     * 评论id
     */
    @ApiParam(name = "评论id",type = "Long",required = true)
    private Long commentId;
    /**
     * 点赞时间
     */
    @ApiParam(name = "点赞时间",type = "Date",required = true)
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
        CommentLike other = (CommentLike) that;
        return ((this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
                && (this.getCommentId() == null ? other.getCommentId() == null : this.getCommentId().equals(other.getCommentId()))
                && (this.getLikeDate() == null ? other.getLikeDate() == null : this.getLikeDate().equals(other.getLikeDate())));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getCommentId() == null) ? 0 : getCommentId().hashCode());
        result = prime * result + ((getLikeDate() == null) ? 0 : getLikeDate().hashCode());
        return result;
    }

}
