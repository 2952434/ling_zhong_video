package com.lingzhong.video.mapper;

import com.lingzhong.video.bean.po.UserAttention;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingzhong.video.bean.vo.AttentionUserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ljx
 * @description 针对表【user_attention】的数据库操作Mapper
 * @createDate 2023-10-27 20:30:22
 * @Entity com.lingzhong.video.bean.po.UserAttention
 */
public interface UserAttentionMapper extends BaseMapper<UserAttention> {

    /**
     * 获取关注我的人的列表
     *
     * @param userId 登录用户id
     * @param page   页数
     * @param count  每页多少条
     * @return List<AttentionUserVo>
     */
    List<AttentionUserVo> getAttentionMyPeople(@Param("userId") Integer userId, @Param("page") Integer page, @Param("count") Integer count);


    /**
     * 获取我关注的人的列表
     *
     * @param userId 登录用户id
     * @param page   页数
     * @param count  每页多少条
     * @return List<AttentionUserVo>
     */
    List<AttentionUserVo> getMyAttentionPeople(@Param("userId") Integer userId, @Param("page") Integer page, @Param("count") Integer count);
}




