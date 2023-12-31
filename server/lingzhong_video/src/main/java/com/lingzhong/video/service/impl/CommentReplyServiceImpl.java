package com.lingzhong.video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lingzhong.video.bean.po.CommentLike;
import com.lingzhong.video.bean.po.CommentReply;
import com.lingzhong.video.service.CommentLikeService;
import com.lingzhong.video.service.CommentReplyService;
import com.lingzhong.video.mapper.CommentReplyMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CommentReplyServiceImpl implements CommentReplyService {

    private final CommentReplyMapper commentReplyMapper;

    private final CommentLikeService commentLikeService;

    public CommentReplyServiceImpl(CommentReplyMapper commentReplyMapper, CommentLikeService commentLikeService) {
        this.commentReplyMapper = commentReplyMapper;
        this.commentLikeService = commentLikeService;
    }

    @Override
    public Long insertNewComment(CommentReply commentReply) {
        if (commentReply.getCommentFid() == null) {
            commentReply.setCommentFid(-1L);
        }
        if (commentReply.getCommentLike() == null) {
            commentReply.setCommentLike(0L);
        }
        /*
          使用时间戳生成唯一评论id
         */
        long commentId = System.currentTimeMillis();
        commentReply.setCommentId(commentId);
        /*
          保存评论
         */
        int innerStatus = commentReplyMapper.insert(commentReply);
        return innerStatus >= 0 ? commentId : null;
    }

    @Override
    public CommentReply selectByCommentId(Long commentId) {
        QueryWrapper<CommentReply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("comment_id", commentId);
        return commentReplyMapper.selectOne(queryWrapper);
    }

    @Override
    public List<CommentReply> getAllCommentByVideoId(Integer videoId) {
        /*
          获取视频下所有评论
         */
        QueryWrapper<CommentReply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("video_id", videoId);
        List<CommentReply> commentReplies = commentReplyMapper.selectList(queryWrapper);
        /*
          封装
         */
        return commentReplies.stream().filter(commentReply -> commentReply.getCommentFid() == -1)
                .peek(fatherComment -> fatherComment.setSonCommentReplyList(getChildrenComment(fatherComment, commentReplies)))
                .sorted(Comparator.comparingLong(CommentReply::getCommentLike)).collect(Collectors.toList());
    }

    /**
     * 封装所有评论下的回复
     *
     * @param fatherComment 父评论实体
     * @param allComment    所有评论实体
     * @return List<CommentReply>
     */
    private List<CommentReply> getChildrenComment(CommentReply fatherComment, List<CommentReply> allComment) {
        return allComment.stream().filter(commentReply -> commentReply.getCommentFid() == fatherComment.getCommentId().intValue())
                .peek(commentReply -> commentReply.setSonCommentReplyList(getChildrenComment(commentReply, allComment))).collect(Collectors.toList());
    }

    @Override
    public List<CommentReply> getCommentByUserId(Integer userId) {
        QueryWrapper<CommentReply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return commentReplyMapper.selectList(queryWrapper);
    }

    @Override
    public Integer delUserComment(Integer userId, Long commentId) {
        QueryWrapper<CommentReply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("comment_id", commentId).eq("user_id", userId);
        return commentReplyMapper.delete(queryWrapper);
    }

    @Override
    public Integer updateCommentLike(CommentReply commentReply) {
        QueryWrapper<CommentReply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("comment_id", commentReply.getCommentId());
        /*
          查询评论的用户id
         */
        CommentReply commentUser = commentReplyMapper.selectOne(queryWrapper);
        /*
          新增点赞记录
         */
        CommentLike commentLike = new CommentLike();
        commentLike.setUserId(commentUser.getUserId());
        commentLike.setCommentId(commentUser.getCommentId());
        commentLike.setBeUserId(commentUser.getUserId());
        commentLike.setLikeDate(commentUser.getReplyDate());
        commentLikeService.addNewLike(commentLike);

        return commentReplyMapper.update(commentReply, queryWrapper);
    }

    @Override
    public CommentReply getCommentByUserIdAndCommentId(Integer userId, Long commentId) {
        QueryWrapper<CommentReply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("comment_id", commentId);
        queryWrapper.eq("user_id", userId);
        return commentReplyMapper.selectOne(queryWrapper);
    }
}




