package com.lingzhong.video.service.impl;

import com.lingzhong.video.bean.po.CommentLike;
import com.lingzhong.video.bean.po.VideoLike;
import com.lingzhong.video.service.InformationService;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class InformationServiceImpl implements InformationService {

    private RedisTemplate<String , Object> redisTemplate;

    private HashOperations<String , String , Object> hashOperations;

    private final String VIDEO_LIKE = "video_like";

    private final String COMMENT_LIKE = "comment_like";

    public InformationServiceImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void innerNewLikeInformation(VideoLike videoLike) {

        Map<String , Object> map = new HashMap<>();
        map.put("userId",videoLike.getUserId());
        map.put("beUserId",videoLike.getBeUserId());
        map.put("videoId",videoLike.getVideoId());
        map.put("likeDate",videoLike.getLikeDate());
        hashOperations.putAll(VIDEO_LIKE , map);

    }

    @Override
    public void innerNewLikeCommentInformation(CommentLike commentLike) {
        Map<String , Object> map = new HashMap<>();
        map.put("userId",commentLike.getUserId());
        map.put("beUserId",commentLike.getBeUserId());
        map.put("videoId",commentLike.getCommentId());
        map.put("likeDate",commentLike.getLikeDate());

        hashOperations.putAll(COMMENT_LIKE , map);

    }
}
