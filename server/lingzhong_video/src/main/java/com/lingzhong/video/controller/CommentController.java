package com.lingzhong.video.controller;

import com.lingzhong.video.bean.po.CommentLike;
import com.lingzhong.video.bean.po.CommentReply;
import com.lingzhong.video.bean.po.User;
import com.lingzhong.video.bean.vo.RespBean;
import com.lingzhong.video.service.CommentLikeService;
import com.lingzhong.video.service.CommentReplyService;
import com.lingzhong.video.service.InformationService;
import com.lingzhong.video.service.VideoDataService;
import com.lingzhong.video.utils.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(tags = "评论回复接口")
@RequestMapping("/lingzhong/comment")
@Validated
public class CommentController {

    private final CommentReplyService commentReplyService;

    private final CommentLikeService commentLikeService;

    private final VideoDataService videoDataService;

    private final InformationService informationService;

    private static final Integer ADD_NUM = 1;
    private static final Integer SUBTRACT_NUM = -1;

    public CommentController(CommentReplyService commentReplyService, CommentLikeService commentLikeService,
                             VideoDataService videoDataService, InformationService informationService) {
        this.commentReplyService = commentReplyService;
        this.commentLikeService = commentLikeService;
        this.videoDataService = videoDataService;
        this.informationService = informationService;
    }

    @ApiOperation(value = "发表评论或回复")
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "videoId", value = "视频id", required = true, dataTypeClass = Integer.class, example = ""),
            @ApiImplicitParam(name = "commentTxt", value = "评论内容", required = true, dataTypeClass = String.class, example = "")
    })
    public RespBean<Long> sendComment(@RequestParam("videoId") Integer videoId, @RequestParam("commentTxt") String commentTxt) {
        User user = LoginUser.getUser();
        if (user == null) {
            return RespBean.error("无法操作");
        }
        Integer userId = user.getUserId();
        CommentReply commentReply = new CommentReply();
        commentReply.setVideoId(videoId);
        commentReply.setCommentTxt(commentTxt);
        commentReply.setUserId(userId);
        commentReply.setReplyDate(new Date());

        /*
          添加评论回复记录
         */
        Long result = commentReplyService.insertNewComment(commentReply);
        /*
          视频评论数增加
         */
        videoDataService.updateVideoCommentNum(commentReply.getVideoId(), ADD_NUM);

        /*
          消息通知
         */
        commentReply.setCommentId(result);
        if (commentReply.getCommentFid() != null) {
            informationService.innerNewCommentInformation(commentReply);
        } else {
            informationService.innerVideoCommentInformation(commentReply);
        }
        return RespBean.ok(result);
    }

    @ApiOperation(value = "获得视频下所有评论及回复")
    @RequestMapping(value = "/get/{videoId}", method = RequestMethod.GET)
    @ApiImplicitParam(name = "videoId", value = "视频id", required = true, dataTypeClass = Integer.class, example = "1")
    public RespBean<List<CommentReply>> getVideoAllComment(@PathVariable("videoId") Integer videoId) {
        List<CommentReply> resultList = commentReplyService.getAllCommentByVideoId(videoId);
        return RespBean.ok(resultList);
    }

    @ApiOperation(value = "获得用户发表的评论")
    @RequestMapping(value = "/get/user", method = RequestMethod.GET)
    public RespBean<List<CommentReply>> getUserAllComment() {
        User user = LoginUser.getUser();
        if (user == null) {
            return RespBean.error("无法操作");
        }
        Integer userId = user.getUserId();
        List<CommentReply> resultList = commentReplyService.getCommentByUserId(userId);
        return RespBean.ok(resultList);
    }

    @ApiOperation(value = "点赞评论，更改评论点赞数")
    @RequestMapping(value = "/like", method = RequestMethod.PUT)
    @ApiImplicitParam(name = "commentId", value = "评论id", required = true, dataTypeClass = Long.class, example = "")
    public RespBean<Integer> likeThisComment(@RequestParam("commentId") Long commentId) {
        User user = LoginUser.getUser();
        if (user == null) {
            return RespBean.error("无法操作");
        }
        Integer userId = user.getUserId();

        CommentReply commentReply = commentReplyService.selectByCommentId(commentId);

        CommentLike commentLike = new CommentLike();
        commentLike.setCommentId(commentId);
        commentLike.setBeUserId(commentReply.getUserId());
        commentLike.setUserId(userId);
        commentLike.setLikeDate(new Date());

        Integer addNewLikeStatus = commentLikeService.addNewLike(commentLike);

        /*
          消息推送
         */
        informationService.innerNewLikeCommentInformation(commentLike);

        /*
          视频评论量增加
         */
        videoDataService.updateVideoCommentNum(commentReply.getVideoId(), ADD_NUM);


        return RespBean.ok(addNewLikeStatus);
    }

    @ApiOperation(value = "取消点赞评论，更改评论点赞数")
    @RequestMapping(value = "/unlike", method = RequestMethod.DELETE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "videoId", value = "视频id", required = true, dataTypeClass = Integer.class, example = ""),
            @ApiImplicitParam(name = "commentId", value = "评论id", required = true, dataTypeClass = Long.class, example = "")
    })
    public RespBean<Integer> unLikeThisComment(@RequestParam("videoId") Integer videoId, @RequestParam("commentId") Long commentId) {
        User user = LoginUser.getUser();
        if (user == null) {
            return RespBean.error("无法操作");
        }
        Integer userId = user.getUserId();

        Integer delStatus = commentLikeService.delCommentLike(userId, commentId);

        /*
          视频评论量减少
         */
        videoDataService.updateVideoCommentNum(videoId, SUBTRACT_NUM);


        return RespBean.ok(delStatus);
    }

    @ApiOperation(value = "判断是否是该用户的评论")
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    @ApiImplicitParam(name = "commentId", value = "评论id", required = true, dataTypeClass = Long.class, example = "1")
    public RespBean<Boolean> checkUserComment(@RequestParam("commentId") Long commentId) {
        User user = LoginUser.getUser();
        if (user == null) {
            return RespBean.error("无法操作");
        }
        Integer userId = user.getUserId();
        CommentReply userComment = commentReplyService.getCommentByUserIdAndCommentId(userId, commentId);
        return RespBean.ok(userComment != null);
    }

    @ApiOperation(value = "删除评论")
    @RequestMapping(value = "/del", method = RequestMethod.DELETE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "videoId", value = "视频id", required = true, dataTypeClass = Integer.class, example = "1"),
            @ApiImplicitParam(name = "commentId", value = "评论id", required = true, dataTypeClass = Long.class, example = "")
    })
    public RespBean<Integer> deleteMyComment(@RequestParam("videoId") Integer videoId, @RequestParam("commentId") Long commentId) {
        User user = LoginUser.getUser();
        if (user == null) {
            return RespBean.error("无法操作");
        }
        Integer userId = user.getUserId();
        /*
          删除评论和该评论的所有点赞记录
         */
        Integer delUserComment = commentReplyService.delUserComment(userId, commentId);
        commentLikeService.delListByCommentId(commentId);
        /*
          减少视频评论数
         */
        videoDataService.updateVideoCommentNum(videoId, SUBTRACT_NUM);
        return RespBean.ok(delUserComment);
    }


    @ApiOperation(value = "获取该视频下此用户点赞过的评论")
    @RequestMapping(value = "/user/liked/comment", method = RequestMethod.GET)
    @ApiImplicitParam(name = "videoId", value = "视频id", required = true, dataTypeClass = Integer.class, example = "1")
    public RespBean<List<CommentReply>> getUserLikedComments(@RequestParam("videoId") Integer videoId) {
        User user = LoginUser.getUser();
        if (user == null) {
            return RespBean.error("无法操作");
        }
        Integer userId = user.getUserId();
        /*
          获取视频下所有评论及回复
         */
        List<CommentReply> theVideoComments = commentReplyService.getAllCommentByVideoId(videoId);
        /*
          获取用户点赞的所有评论
         */
        List<CommentLike> commentList = commentLikeService.getUserLikedCommentList(userId);
        List<Long> likedCommentList = commentList.stream().map(CommentLike::getCommentId).collect(Collectors.toList());

        List<CommentReply> userLikedCommentList = theVideoComments.stream().filter(theVideoComment -> likedCommentList.contains(theVideoComment.getCommentId())).collect(Collectors.toList());

        return RespBean.ok(userLikedCommentList);
    }

    @ApiOperation(value = "判断用户是否点赞过该评论")
    @RequestMapping(value = "/check/liked", method = RequestMethod.GET)
    @ApiImplicitParam(name = "commentId", value = "评论id", required = true, dataTypeClass = Long.class, example = "")
    public RespBean<Boolean> checkUserLikedComments(@RequestParam("commentId") Long commentId) {
        User user = LoginUser.getUser();
        if (user == null) {
            return RespBean.error("无法操作");
        }
        Integer userId = user.getUserId();
        CommentLike commentLike = commentLikeService.selectByUserIdAndCommentId(userId, commentId);
        return RespBean.ok(commentLike != null);
    }

    @ApiOperation(value = "通过评论id查看评论详细内容")
    @RequestMapping(value = "/look", method = RequestMethod.GET)
    @ApiImplicitParam(name = "commentId", value = "评论id", required = true, dataTypeClass = Long.class, example = "")
    public RespBean<CommentReply> lookComment(@RequestParam("commentId") Long commentId) {
        CommentReply commentReply = commentReplyService.selectByCommentId(commentId);
        return RespBean.ok(commentReply);
    }
}
