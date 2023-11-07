package com.lingzhong.video.mapper;

import com.lingzhong.video.bean.po.CommentReply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ljx
 * @description 针对表【comment_reply】的数据库操作Mapper
 * @createDate 2023-10-27 20:30:22
 * @Entity com.lingzhong.video.bean.po.CommentReply
 */
@Repository
public interface CommentReplyMapper extends BaseMapper<CommentReply> {

}




