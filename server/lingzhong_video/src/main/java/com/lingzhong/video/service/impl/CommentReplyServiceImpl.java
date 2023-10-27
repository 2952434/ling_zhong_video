package com.lingzhong.video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingzhong.video.bean.po.CommentReply;
import com.lingzhong.video.service.CommentReplyService;
import com.lingzhong.video.mapper.CommentReplyMapper;
import org.aspectj.apache.bcel.generic.RET;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author ljx
* @description 针对表【comment_reply】的数据库操作Service实现
* @createDate 2023-10-27 20:30:22
*/
@Service
public class CommentReplyServiceImpl implements CommentReplyService{

    private CommentReplyMapper commentReplyMapper;

    public CommentReplyServiceImpl(CommentReplyMapper commentReplyMapper) {
        this.commentReplyMapper = commentReplyMapper;
    }

    @Override
    public String insertNewComment(CommentReply commentReply) {
        int innerStatus = commentReplyMapper.insert(commentReply);
        return innerStatus >= 0 ? "发表成功" : "发表失败";
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
        List<CommentReply> fatherCommentList = commentReplies.stream().filter(commentReply -> commentReply.getCommentFid() == -1).collect(Collectors.toList());
        List<CommentReply> sonCommentList = commentReplies.stream().filter(commentReply -> commentReply.getCommentFid() != -1).collect(Collectors.toList());
        fatherCommentList.forEach(fatherComment -> {
            List<CommentReply> replyList = sonCommentList.stream()
                    .filter(sonComment -> sonComment.getCommentFid().intValue() == fatherComment.getCommentId().intValue())
                    .collect(Collectors.toList());
            fatherComment.setSonCommentReplyList(replyList);
        });

        return fatherCommentList;
    }

    @Override
    public List<CommentReply> getCommentByUserId(Integer userId) {
        QueryWrapper<CommentReply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id" , userId);
        return commentReplyMapper.selectList(queryWrapper);
    }
}




