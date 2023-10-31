package com.lingzhong.video.service;

import com.lingzhong.video.bean.po.CommentLike;
import com.lingzhong.video.bean.po.CommentReply;
import com.lingzhong.video.bean.po.VideoCollect;
import com.lingzhong.video.bean.po.VideoLike;

import java.util.List;

public interface InformationService {

    /**
     * 推送视频用户被喜欢消息
     * @param videoLike
     */
    public void innerNewLikeInformation(VideoLike videoLike);

    /**
     * 推送视频用户被收藏消息
     */
    public void innerNewCollectInformation(VideoCollect videoCollect);

    /**
     * 推送评论用户被点赞的消息
     * @param commentLike
     */
    public void innerNewLikeCommentInformation(CommentLike commentLike);

    /**
     * 推送评论被回复的消息
     */
    public void innerNewCommentInformation(CommentReply commentReply);

    /**
     * 推送视频被评论的消息
     */
    public void innerVideoCommentInformation(CommentReply commentReply);

    /**
     * 接收到评论点赞消息
     */
    public List<CommentLike> getCommentLikeInformation(Integer userId);

    /**
     * 接收到视频点赞消息
     */
    public List<VideoLike> getVideoLikeInformation(Integer userId);

    /**
     * 接收到视频收藏消息
     */
    public List<VideoCollect> getVideoCollectInformation(Integer userId);

    /**
     * 接收视频评论信息
     */
    public List<CommentReply> getVideoCommentInformation(Integer userId);

    /**
     * 接收评论回复信息
     */
    public List<CommentReply> getCommentInformation(Integer userId);



}
