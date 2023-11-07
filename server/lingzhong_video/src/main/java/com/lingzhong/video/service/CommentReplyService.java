package com.lingzhong.video.service;

import com.lingzhong.video.bean.po.CommentReply;

import java.util.List;

/**
* @author ljx
* @description 针对表【comment_reply】的数据库操作Service
* @createDate 2023-10-27 20:30:22
*/
public interface CommentReplyService  {

    /**
     * 发表评论或回复
     * @param commentReply 评论或回复实体
     * @return Long
     */
    Long insertNewComment(CommentReply commentReply);

    /**
     * 通过评论id获取评论信息
     * @param commentId 评论Id
     * @return CommentReply
     */
    CommentReply selectByCommentId(Long commentId);

    /**
     * 获取视频下所有评论及回复
     * @param videoId 视频Id
     * @return List<CommentReply>
     */
    List<CommentReply> getAllCommentByVideoId(Integer videoId);

    /**
     * 获取用户的评论
     * @param userId 用户Id
     * @return List<CommentReply>
     */
    List<CommentReply> getCommentByUserId(Integer userId);

    /**
     * 删除本用户评论
     * @param userId 用户Id
     * @param commentId 评论Id
     * @return Integer
     */
    Integer delUserComment(Integer userId, Long commentId);

    /**
     * 更新评论数
     * @param commentReply 评论实体
     * @return Integer
     */
    Integer updateCommentLike(CommentReply commentReply);

    /**
     * 判断是否是该用户的评论
     * @param userId 用户Id
     * @param commentId 评论Id
     * @return CommentReply
     */
    CommentReply getCommentByUserIdAndCommentId(Integer userId , Long commentId);


}
