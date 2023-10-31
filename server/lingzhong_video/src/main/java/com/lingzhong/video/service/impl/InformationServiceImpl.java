package com.lingzhong.video.service.impl;

import com.lingzhong.video.bean.po.*;
import com.lingzhong.video.service.CommentReplyService;
import com.lingzhong.video.service.InformationService;
import com.lingzhong.video.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class InformationServiceImpl implements InformationService {

    private RedisTemplate<String , Object> redisTemplate;

    @Autowired
    private CommentReplyService commentReplyService;
    @Autowired
    private VideoService videoService;

    private final String VIDEO_LIKE = "video_like";

    private final String COLLECT_LIKE = "collect_like";
    private final String COMMENT_LIKE = "comment_like";

    private final String COMMENT = "comment";

    private final String VIDEO_COMMENT = "video_comment";

    public InformationServiceImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void innerNewLikeInformation(VideoLike videoLike) {
        HashOperations<String, String, VideoLike> hashOperations = redisTemplate.opsForHash();
        String filed = String.valueOf(videoLike.getLikeDate());
        hashOperations.put(VIDEO_LIKE + videoLike.getBeUserId() , filed , videoLike);
    }

    @Override
    public void innerNewCollectInformation(VideoCollect videoCollect) {
        HashOperations<String, String, VideoCollect> hashOperations = redisTemplate.opsForHash();
        String filed = String.valueOf(videoCollect.getCollectDate());
        hashOperations.put(COLLECT_LIKE + videoCollect.getBeUserId() , filed , videoCollect);
    }

    @Override
    public void innerNewLikeCommentInformation(CommentLike commentLike) {
        HashOperations<String, String, CommentLike> hashOperations = redisTemplate.opsForHash();
        String filed = String.valueOf(commentLike.getLikeDate());
        hashOperations.put(COMMENT_LIKE + commentLike.getBeUserId() , filed , commentLike);
    }

    @Override
    public void innerNewCommentInformation(CommentReply commentReply) {
        HashOperations<String, String, CommentReply> hashOperations = redisTemplate.opsForHash();
        String filed = String.valueOf(commentReply.getReplyDate());
        /**
         * 查询评论的父用户id
         */
        CommentReply beUserComment = commentReplyService.selectByCommentId(commentReply.getCommentId());

        hashOperations.put(COMMENT + beUserComment.getUserId() , filed , commentReply);
    }

    @Override
    public void innerVideoCommentInformation(CommentReply commentReply) {
        HashOperations<String, String, CommentReply> hashOperations = redisTemplate.opsForHash();
        String filed = String.valueOf(commentReply.getReplyDate());
        /**
         * 查询视频的发表用户id
         */
        Video beUserVideo = videoService.getVideoById(commentReply.getVideoId());

        hashOperations.put(VIDEO_COMMENT + beUserVideo.getVideoUserId() , filed , commentReply);
    }

    @Override
    public List<CommentLike> getCommentLikeInformation(Integer userId) {
        String key = COMMENT_LIKE + userId;
        HashOperations<String, String, CommentLike> hashOperations = redisTemplate.opsForHash();
        Map<String, CommentLike> entries = hashOperations.entries(key);
        List<CommentLike> commentLikeList = new ArrayList<>(entries.values());
        /**
         * 清除消息
         */
        hashOperations.getOperations().delete(key);
        return commentLikeList;
    }

    @Override
    public List<VideoLike> getVideoLikeInformation(Integer userId) {
        String key = VIDEO_LIKE + userId;
        HashOperations<String, String, VideoLike> hashOperations = redisTemplate.opsForHash();
        Map<String, VideoLike> entries = hashOperations.entries(key);
        List<VideoLike> videoLikeList = new ArrayList<>(entries.values());
        /**
         * 清除消息
         */
        hashOperations.getOperations().delete(key);
        return videoLikeList;
    }

    @Override
    public List<VideoCollect> getVideoCollectInformation(Integer userId) {
        String key = COLLECT_LIKE + userId;
        HashOperations<String, String, VideoCollect> hashOperations = redisTemplate.opsForHash();
        Map<String, VideoCollect> entries = hashOperations.entries(key);
        List<VideoCollect> videoCollectList = new ArrayList<>(entries.values());
        /**
         * 清除消息
         */
        hashOperations.getOperations().delete(key);
        return videoCollectList;
    }

    @Override
    public List<CommentReply> getVideoCommentInformation(Integer userId) {
        String key = VIDEO_COMMENT + userId;
        HashOperations<String, String, CommentReply> hashOperations = redisTemplate.opsForHash();
        Map<String, CommentReply> entries = hashOperations.entries(key);
        List<CommentReply> commentReplyList = new ArrayList<>(entries.values());
        /**
         * 清除消息
         */
        hashOperations.getOperations().delete(key);
        return commentReplyList;
    }

    @Override
    public List<CommentReply> getCommentInformation(Integer userId) {
        String key = COMMENT + userId;
        HashOperations<String, String, CommentReply> hashOperations = redisTemplate.opsForHash();
        Map<String, CommentReply> entries = hashOperations.entries(key);
        List<CommentReply> commentReplyList = new ArrayList<>(entries.values());
        /**
         * 清除消息
         */
        hashOperations.getOperations().delete(key);
        return commentReplyList;
    }
}
