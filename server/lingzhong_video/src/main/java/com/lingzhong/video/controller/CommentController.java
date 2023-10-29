package com.lingzhong.video.controller;

import com.lingzhong.video.bean.po.CommentLike;
import com.lingzhong.video.bean.po.CommentReply;
import com.lingzhong.video.service.CommentLikeService;
import com.lingzhong.video.service.CommentReplyService;
import com.lingzhong.video.service.VideoDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(tags = "评论回复接口")
@RequestMapping("/lingzhong/comment")
@Validated
public class CommentController {

    private CommentReplyService commentReplyService;

    private CommentLikeService commentLikeService;

    private VideoDataService videoDataService;

    private static final Integer ADD_NUM = 1;
    private static final Integer SUBTRACT_NUM = -1;

    public CommentController(CommentReplyService commentReplyService, CommentLikeService commentLikeService, VideoDataService videoDataService) {
        this.commentReplyService = commentReplyService;
        this.commentLikeService = commentLikeService;
        this.videoDataService = videoDataService;
    }

    @ApiOperation(value = "发表评论或回复")
    @RequestMapping(value = "/send" , method = RequestMethod.PUT)
    @ApiImplicitParam(name = "commentReply" , value = "评论所需信息" , required = true , dataTypeClass = CommentReply.class , example = "")
    public ResponseEntity<Long>  sendComment(@RequestBody @Valid CommentReply commentReply){
        /**
         * 添加评论回复记录
         */
        Long result = commentReplyService.insertNewComment(commentReply);
        /**
         * 视频评论数增加
         */
        Integer videoCommentInner = videoDataService.updateVideoCommentNum(commentReply.getVideoId(), ADD_NUM);
        return new ResponseEntity<Long>(result , HttpStatus.OK);
    }

    @ApiOperation(value = "获得视频下所有评论及回复")
    @RequestMapping(value = "/get/{videoId}" , method = RequestMethod.GET)
    @ApiImplicitParam(name = "videoId" , value = "视频id" , required = true , dataTypeClass = Integer.class , example = "1")
    public ResponseEntity<List<CommentReply>>  getVideoAllComment(@PathVariable("videoId") Integer videoId) {
        List<CommentReply> resultList = commentReplyService.getAllCommentByVideoId(videoId);
        return new ResponseEntity<List<CommentReply>>(resultList , HttpStatus.OK);
    }

    @ApiOperation(value = "获得用户发表的评论")
    @RequestMapping(value = "/get/user/{userId}" , method = RequestMethod.GET)
    @ApiImplicitParam(name = "userId" , value = "用户id" , required = true , dataTypeClass = Integer.class , example = "1")
    public ResponseEntity<List<CommentReply>> getUserAllComment(@PathVariable("userId") Integer userId){
        List<CommentReply> resultList = commentReplyService.getCommentByUserId(userId);
        return new ResponseEntity<List<CommentReply>>(resultList , HttpStatus.OK);
    }

    @ApiOperation(value = "点赞或取消点赞，更改评论点赞数")
    @RequestMapping(value = "/like" , method = RequestMethod.POST)
    @ApiImplicitParam(name = "commentReply" , value = "评论所需信息" , required = true , dataTypeClass = CommentReply.class , example = "")
    public ResponseEntity<Integer> likeThisComment(@RequestBody CommentReply commentReply){
        Integer updateCommentLike = commentReplyService.updateCommentLike(commentReply);
        /**
         * 判断是否点赞过
         */
        CommentLike commentLike = commentLikeService.selectByUserIdAndCommentId(commentReply.getUserId(), commentReply.getCommentId());
        if (commentLike != null){
            commentLikeService.delCommentLike(commentReply.getUserId() , commentReply.getCommentId());
        }
        else {
            CommentLike newCommentLike = new CommentLike();
            newCommentLike.setUserId(commentReply.getUserId());
            newCommentLike.setCommentId(commentReply.getCommentId());
            newCommentLike.setLikeDate(commentReply.getReplyDate());
            commentLikeService.addNewLike(newCommentLike);
        }

        return new ResponseEntity<Integer>(updateCommentLike , HttpStatus.OK);
    }

    @ApiOperation(value = "判断是否是该用户的评论")
    @RequestMapping(value = "/check" , method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commentId" , value = "评论id" , required = true , dataTypeClass = Long.class , example = "1"),
            @ApiImplicitParam(name = "userId" , value = "用户id" , required = true , dataTypeClass = Integer.class , example = "1")
    })
    public ResponseEntity<Boolean> checkUserComment(@RequestParam("commentId") Long commentId ,@RequestParam("userId") Integer userId){
        CommentReply userComment = commentReplyService.getCommentByUserIdAndCommentId(userId, commentId);
        return new ResponseEntity<Boolean>(userComment != null, HttpStatus.OK);
    }

    @ApiOperation(value = "删除评论")
    @RequestMapping(value = "/del" , method = RequestMethod.DELETE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "videoId" , value = "视频id" , required = true , dataTypeClass = Integer.class , example = "1"),
            @ApiImplicitParam(name = "commentId" , value = "评论id" , required = true , dataTypeClass = Long.class , example = "1654651321")
    })
    public ResponseEntity<Integer> deleteMyComment(@RequestParam("videoId") Integer videoId , @RequestParam("commentId") Long commentId){
        /**
         * 删除评论和该评论的所有点赞记录
         */
        Integer delUserComment = commentReplyService.delUserComment(commentId);
        Integer delListByCommentId = commentLikeService.delListByCommentId(commentId);
        /**
         * 减少视频评论数
         */
        Integer updateStatus = videoDataService.updateVideoCommentNum(videoId, SUBTRACT_NUM);
        return new ResponseEntity<Integer>(delUserComment , HttpStatus.OK);
    }



    @ApiOperation(value = "获取该视频下此用户点赞过的评论")
    @RequestMapping(value = "/user/liked/comment" , method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "videoId" , value = "视频id" , required = true , dataTypeClass = Integer.class , example = "1"),
            @ApiImplicitParam(name = "userId" , value = "用户id" , required = true , dataTypeClass = Integer.class , example = "1")
    })
    public ResponseEntity<List<CommentReply>> getUserLikedComments(@RequestParam("videoId") Integer videoId ,@RequestParam("userId") Integer userId){
        /**
         * 获取视频下所有评论及回复
         */
        List<CommentReply> theVideoComments = commentReplyService.getAllCommentByVideoId(videoId);
        /**
         * 获取用户点赞的所有评论
         */
        List<CommentLike> commentList = commentLikeService.getUserLikedCommentList(userId);
        List<Long> likedCommentList = commentList.stream().map(CommentLike::getCommentId).collect(Collectors.toList());

        List<CommentReply> userLikedCommentList = theVideoComments.stream().filter(theVideoComment -> likedCommentList.contains(theVideoComment.getCommentId())).collect(Collectors.toList());

        return new ResponseEntity<List<CommentReply>>(userLikedCommentList , HttpStatus.OK);
    }
}
