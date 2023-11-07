package com.lingzhong.video.service;

import com.lingzhong.video.bean.po.CommentLike;

import java.util.List;

public interface CommentLikeService {
    /**
     * 添加点赞记录
     *
     * @param commentLike 评论实体
     * @return Integer
     */
    Integer addNewLike(CommentLike commentLike);

    /**
     * 取消（删除）评论记录
     *
     * @param userId    用户Id
     * @param commentId 评论ID
     * @return Integer
     */
    Integer delCommentLike(Integer userId, Long commentId);

    /**
     * 判断是否点赞过
     *
     * @param userId    用户Id
     * @param commentId 评论Id
     * @return CommentLike
     */
    CommentLike selectByUserIdAndCommentId(Integer userId, Long commentId);

    /**
     * 通过评论Id批量删除点赞记录
     *
     * @param commentId 评论Id
     * @return Integer
     */
    Integer delListByCommentId(Long commentId);

    /**
     * 获取用户点赞的评论记录
     *
     * @param userId 用户Id
     * @return List<CommentLike>
     */
    List<CommentLike> getUserLikedCommentList(Integer userId);
}
