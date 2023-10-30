package com.lingzhong.video.service;

import com.lingzhong.video.bean.po.CommentLike;
import com.lingzhong.video.bean.po.VideoLike;

public interface InformationService {

    /**
     * 推送视频用户被喜欢消息
     * @param videoLike
     */
    public void innerNewLikeInformation(VideoLike videoLike);

    /**
     * 推送评论用户被点赞的消息
     * @param commentLike
     */
    public void innerNewLikeCommentInformation(CommentLike commentLike);




}
