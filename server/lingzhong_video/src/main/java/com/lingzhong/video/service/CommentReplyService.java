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
     */
    public Long insertNewComment(CommentReply commentReply);

    /**
     * 通过评论id获取评论信息
     */
    public CommentReply selectByCommentId(Long commentId);

    /**
     * 获取视频下所有评论及回复
     */
    public List<CommentReply> getAllCommentByVideoId(Integer videoId);

    /**
     * 获取用户的评论
     */
    public List<CommentReply> getCommentByUserId(Integer userId);

    /**
     * 删除本用户评论
     * @param userId
     * @param commentId
     */
    public Integer delUserComment(Integer userId, Long commentId);

    /**
     * 更新点赞数
     */
    public Integer updateCommentLike(CommentReply commentReply);

    /**
     * 判断是否是该用户的评论
     */
    public CommentReply getCommentByUserIdAndCommentId(Integer userId , Long commentId);


}
