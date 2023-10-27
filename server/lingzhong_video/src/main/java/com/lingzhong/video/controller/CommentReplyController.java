package com.lingzhong.video.controller;

import com.lingzhong.video.bean.po.CommentReply;
import com.lingzhong.video.service.CommentReplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api(tags = "评论回复接口")
@RequestMapping("/lingzhong/comment")
@Validated
public class CommentReplyController {

    private CommentReplyService commentReplyService;

    public CommentReplyController(CommentReplyService commentReplyService) {
        this.commentReplyService = commentReplyService;
    }

    @ApiOperation(value = "发表评论或回复")
    @RequestMapping(value = "/send" , method = RequestMethod.PUT)
    public ResponseEntity<String>  sendComment(@RequestBody @Valid CommentReply commentReply){
        String result = commentReplyService.insertNewComment(commentReply);
        return new ResponseEntity<String>(result , HttpStatus.OK);
    }

    @ApiOperation(value = "获得视频下所有评论及回复")
    @RequestMapping(value = "/get/{videoId}" , method = RequestMethod.GET)
    public ResponseEntity<List<CommentReply>>  getVideoAllComment(@PathVariable("videoId") Integer videoId) {
        List<CommentReply> resultList = commentReplyService.getAllCommentByVideoId(videoId);
        return new ResponseEntity<List<CommentReply>>(resultList , HttpStatus.OK);
    }

    @ApiOperation(value = "获得用户发表的评论")
    @RequestMapping(value = "/get/user/{userId}" , method = RequestMethod.GET)
    public ResponseEntity<List<CommentReply>> getUserAllComment(@PathVariable("userId") Integer userId){
        List<CommentReply> resultList = commentReplyService.getCommentByUserId(userId);
        return new ResponseEntity<List<CommentReply>>(resultList , HttpStatus.OK);
    }
}
