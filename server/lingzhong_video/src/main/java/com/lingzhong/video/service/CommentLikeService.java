package com.lingzhong.video.service;

import com.lingzhong.video.bean.po.CommentLike;

import java.util.List;

public interface CommentLikeService {
    /**
     * 添加点赞记录
     */
    public Integer addNewLike(CommentLike commentLike);

    /**
     * 取消（删除）点赞记录
     */
    public Integer delCommentLike(Integer userId , Long commentId);

    /**
     * 判断是否点赞过
     * @return
     */
    public CommentLike selectByUserIdAndCommentId(Integer userId , Long commentId);

    /**
     * 通过评论id批量删除点赞记录
     * @param commentId
     */
    public Integer delListByCommentId(Long commentId);

    /**
     * 获取用户点赞的评论记录
     */
    public List<CommentLike> getUserLikedCommentList(Integer userId);
}
