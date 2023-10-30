package com.lingzhong.video.controller;

import com.lingzhong.video.bean.po.CommentLike;
import com.lingzhong.video.bean.po.CommentReply;
import com.lingzhong.video.bean.po.User;
import com.lingzhong.video.bean.vo.RespBean;
import com.lingzhong.video.service.CommentLikeService;
import com.lingzhong.video.service.CommentReplyService;
import com.lingzhong.video.service.VideoDataService;
import com.lingzhong.video.utils.LoginUser;
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
    public RespBean<Long> sendComment(@RequestBody @Valid CommentReply commentReply){
        User user = LoginUser.getUser();
        if (user == null)
            return RespBean.error("无法操作");
        Integer userId = user.getUserId();
        commentReply.setUserId(userId);
        /**
         * 添加评论回复记录
         */
        Long result = commentReplyService.insertNewComment(commentReply);
        /**
         * 视频评论数增加
         */
        Integer videoCommentInner = videoDataService.updateVideoCommentNum(commentReply.getVideoId(), ADD_NUM);
        return RespBean.ok(result);
    }

    @ApiOperation(value = "获得视频下所有评论及回复")
    @RequestMapping(value = "/get/{videoId}" , method = RequestMethod.GET)
    @ApiImplicitParam(name = "videoId" , value = "视频id" , required = true , dataTypeClass = Integer.class , example = "1")
    public RespBean<List<CommentReply>>  getVideoAllComment(@PathVariable("videoId") Integer videoId) {
        List<CommentReply> resultList = commentReplyService.getAllCommentByVideoId(videoId);
        return RespBean.ok(resultList);
    }

    @ApiOperation(value = "获得用户发表的评论")
    @RequestMapping(value = "/get/user" , method = RequestMethod.GET)
    public RespBean<List<CommentReply>> getUserAllComment(){
        User user = LoginUser.getUser();
        if (user == null)
            return RespBean.error("无法操作");
        Integer userId = user.getUserId();
        List<CommentReply> resultList = commentReplyService.getCommentByUserId(userId);
        return RespBean.ok(resultList);
    }

    @ApiOperation(value = "点赞或取消点赞，更改评论点赞数")
    @RequestMapping(value = "/like" , method = RequestMethod.POST)
    @ApiImplicitParam(name = "commentReply" , value = "评论所需信息" , required = true , dataTypeClass = CommentReply.class , example = "")
    public RespBean<Integer> likeThisComment(@RequestBody CommentReply commentReply){
        User user = LoginUser.getUser();
        if (user == null)
            return RespBean.error("无法操作");
        Integer userId = user.getUserId();
        commentReply.setUserId(userId);
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

        return RespBean.ok(updateCommentLike);
    }

    @ApiOperation(value = "判断是否是该用户的评论")
    @RequestMapping(value = "/check" , method = RequestMethod.GET)
    @ApiImplicitParam(name = "commentId" , value = "评论id" , required = true , dataTypeClass = Long.class , example = "1")
    public RespBean<Boolean> checkUserComment(@RequestParam("commentId") Long commentId){
        User user = LoginUser.getUser();
        if (user == null)
            return RespBean.error("无法操作");
        Integer userId = user.getUserId();
        CommentReply userComment = commentReplyService.getCommentByUserIdAndCommentId(userId, commentId);
        return RespBean.ok(userComment != null);
    }

    @ApiOperation(value = "删除评论")
    @RequestMapping(value = "/del" , method = RequestMethod.DELETE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "videoId" , value = "视频id" , required = true , dataTypeClass = Integer.class , example = "1"),
            @ApiImplicitParam(name = "commentId" , value = "评论id" , required = true , dataTypeClass = Long.class , example = "1654651321")
    })
    public RespBean<Integer> deleteMyComment(@RequestParam("videoId") Integer videoId , @RequestParam("commentId") Long commentId){
        User user = LoginUser.getUser();
        if (user == null)
            return RespBean.error("无法操作");
        Integer userId = user.getUserId();
        /**
         * 删除评论和该评论的所有点赞记录
         */
        Integer delUserComment = commentReplyService.delUserComment(userId, commentId);
        Integer delListByCommentId = commentLikeService.delListByCommentId(commentId);
        /**
         * 减少视频评论数
         */
        Integer updateStatus = videoDataService.updateVideoCommentNum(videoId, SUBTRACT_NUM);
        return RespBean.ok(delUserComment);
    }



    @ApiOperation(value = "获取该视频下此用户点赞过的评论")
    @RequestMapping(value = "/user/liked/comment" , method = RequestMethod.GET)
    @ApiImplicitParam(name = "videoId" , value = "视频id" , required = true , dataTypeClass = Integer.class , example = "1")
    public RespBean<List<CommentReply>> getUserLikedComments(@RequestParam("videoId") Integer videoId){
        User user = LoginUser.getUser();
        if (user == null)
            return RespBean.error("无法操作");
        Integer userId = user.getUserId();
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

        return RespBean.ok(userLikedCommentList);
    }
}
