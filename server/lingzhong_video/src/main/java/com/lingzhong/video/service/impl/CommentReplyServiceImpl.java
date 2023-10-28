package com.lingzhong.video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lingzhong.video.bean.po.CommentLike;
import com.lingzhong.video.bean.po.CommentReply;
import com.lingzhong.video.service.CommentLikeService;
import com.lingzhong.video.service.CommentReplyService;
import com.lingzhong.video.mapper.CommentReplyMapper;
import com.lingzhong.video.utils.SnowFlake;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CommentReplyServiceImpl implements CommentReplyService{

    private CommentReplyMapper commentReplyMapper;

    private CommentLikeService commentLikeService;

    public CommentReplyServiceImpl(CommentReplyMapper commentReplyMapper, CommentLikeService commentLikeService) {
        this.commentReplyMapper = commentReplyMapper;
        this.commentLikeService = commentLikeService;
    }

    @Override
    public Long insertNewComment(CommentReply commentReply) {
        if (commentReply.getCommentFid() == null){
            commentReply.setCommentFid(-1);
        }
        if (commentReply.getCommentLike() == null){
            commentReply.setCommentLike(0);
        }
        /**
         * 雪花算法生成唯一评论id
         */
        long commentId = System.currentTimeMillis();
        commentReply.setCommentId(commentId);
        /**
         * 保存评论
         */
        int innerStatus = commentReplyMapper.insert(commentReply);
        return innerStatus >= 0 ? commentId : null;
    }

    @Override
    public List<CommentReply> getAllCommentByVideoId(Integer videoId) {
        /**
         * 获取视频下所有评论
         */
        QueryWrapper<CommentReply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("video_id" , videoId);
        List<CommentReply> commentReplies = commentReplyMapper.selectList(queryWrapper);
        /**
         * 封装
         */
         return commentReplies.stream().filter(commentReply -> commentReply.getCommentFid() == -1)
                .peek(fatherComment-> fatherComment.setSonCommentReplyList(getChildrenComment(fatherComment , commentReplies)))
                .sorted(Comparator.comparingInt(CommentReply::getCommentLike)).collect(Collectors.toList());
    }

    /**
     * 封装所有评论下的回复
     * @param fatherComment
     * @param allComment
     * @return
     */
    private List<CommentReply> getChildrenComment(CommentReply fatherComment , List<CommentReply> allComment){
        return allComment.stream().filter(commentReply -> commentReply.getCommentFid() == fatherComment.getCommentId().intValue())
                .peek(commentReply -> commentReply.setSonCommentReplyList(getChildrenComment(commentReply , allComment))).collect(Collectors.toList());
    }

    @Override
    public List<CommentReply> getCommentByUserId(Integer userId) {
        QueryWrapper<CommentReply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id" , userId);
        return commentReplyMapper.selectList(queryWrapper);
    }

    @Override
    public Integer delUserComment(Integer commentId) {
        QueryWrapper<CommentReply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("comment_id" , commentId);
        return commentReplyMapper.delete(queryWrapper);
    }

    @Override
    public Integer updateCommentLike(CommentReply commentReply) {
        QueryWrapper<CommentReply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("comment_id" , commentReply.getCommentId());

        /**
         * 新增点赞记录
         */
        CommentLike commentLike = new CommentLike();
        commentLike.setUserId(commentReply.getUserId());
        commentLike.setCommentId(commentReply.getCommentId());
        commentLike.setLikeDate(commentReply.getReplyDate());
        Integer addNewLike = commentLikeService.addNewLike(commentLike);

        return commentReplyMapper.update(commentReply , queryWrapper);
    }

    @Override
    public CommentReply getCommentByUserIdAndCommentId(Integer userId, Integer commentId) {
        QueryWrapper<CommentReply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("comment_id" , commentId);
        queryWrapper.eq("user_id" , userId);
        return commentReplyMapper.selectOne(queryWrapper);
    }
}




