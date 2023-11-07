package com.lingzhong.video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lingzhong.video.bean.po.CommentLike;
import com.lingzhong.video.mapper.CommentLikeMapper;
import com.lingzhong.video.service.CommentLikeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentLikeServiceImpl implements CommentLikeService {

    private final CommentLikeMapper commentLikeMapper;

    public CommentLikeServiceImpl(CommentLikeMapper commentLikeMapper) {
        this.commentLikeMapper = commentLikeMapper;
    }

    @Override
    public Integer addNewLike(CommentLike commentLike) {
        return commentLikeMapper.insert(commentLike);
    }

    @Override
    public Integer delCommentLike(Integer userId, Long commentId) {
        QueryWrapper<CommentLike> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).eq("comment_id", commentId);
        return commentLikeMapper.delete(queryWrapper);
    }

    @Override
    public CommentLike selectByUserIdAndCommentId(Integer userId, Long commentId) {
        QueryWrapper<CommentLike> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).eq("comment_id", commentId);
        return commentLikeMapper.selectOne(queryWrapper);
    }

    @Override
    public Integer delListByCommentId(Long commentId) {
        QueryWrapper<CommentLike> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("comment_id", commentId);
        return commentLikeMapper.delete(queryWrapper);
    }

    @Override
    public List<CommentLike> getUserLikedCommentList(Integer userId) {
        QueryWrapper<CommentLike> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return commentLikeMapper.selectList(queryWrapper);
    }
}
