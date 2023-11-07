package com.lingzhong.video.service;

import com.lingzhong.video.bean.po.CommentLike;
import com.lingzhong.video.bean.po.CommentReply;
import com.lingzhong.video.bean.po.VideoCollect;
import com.lingzhong.video.bean.po.VideoLike;

import java.util.List;

public interface InformationService {

    /**
     * 推送视频用户被喜欢消息
     *
     * @param videoLike 点赞实体
     */
    void innerNewLikeInformation(VideoLike videoLike);

    /**
     * 推送视频用户被收藏消息
     *
     * @param videoCollect 视频收藏实体
     */
    void innerNewCollectInformation(VideoCollect videoCollect);

    /**
     * 推送评论用户被点赞的消息
     *
     * @param commentLike 评论点赞实体
     */
    void innerNewLikeCommentInformation(CommentLike commentLike);

    /**
     * 推送评论被回复的消息
     *
     * @param commentReply 评论回复实体
     */
    void innerNewCommentInformation(CommentReply commentReply);

    /**
     * 推送视频被评论的消息
     *
     * @param commentReply 评论回复实体
     */
    void innerVideoCommentInformation(CommentReply commentReply);

    /**
     * 接收到评论点赞消息
     *
     * @param userId 用户Id
     * @return List<CommentLike>
     */
    List<CommentLike> getCommentLikeInformation(Integer userId);

    /**
     * 接收到视频点赞消息
     *
     * @param userId 用户Id
     * @return List<VideoLike>
     */
    List<VideoLike> getVideoLikeInformation(Integer userId);

    /**
     * 接收到视频收藏消息
     *
     * @param userId 用户Id
     * @return List<VideoCollect>
     */
    List<VideoCollect> getVideoCollectInformation(Integer userId);

    /**
     * 接收视频评论信息
     *
     * @param userId 用户Id
     * @return List<CommentReply>
     */
    List<CommentReply> getVideoCommentInformation(Integer userId);

    /**
     * 接收评论回复信息
     *
     * @param userId 用户Id
     * @return List<CommentReply>
     */
    List<CommentReply> getCommentInformation(Integer userId);


}
