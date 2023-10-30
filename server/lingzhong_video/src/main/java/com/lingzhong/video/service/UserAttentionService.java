package com.lingzhong.video.service;

import com.lingzhong.video.bean.po.UserAttention;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lingzhong.video.bean.vo.AttentionUserVo;

import java.util.List;

/**
* @author ljx
* @description 针对表【user_attention】的数据库操作Service
* @createDate 2023-10-27 20:30:22
*/
public interface UserAttentionService  {

    /**
     * 根据用户id关注用户
     * @param beUserId 被关注用户的id
     * @return boolean
     */
    boolean attentionUserByUserId(Integer beUserId);


    /**
     * 根据用户id取消对该用户的关注
     * @param beUserId 被取消关注的用户ID
     * @return boolean
     */
    boolean deleteAttentionByUserId(Integer beUserId);

    /**
     * 分页获得关注我的人
     * @param page 页数
     * @return List<AttentionUserVo>
     */
    List<AttentionUserVo> getAttentionMyPeople(Integer page);


    /**
     * 分页获得我关注的人
     * @param page 页数
     * @return List<AttentionUserVo>
     */
    List<AttentionUserVo> getMyAttentionPeople(Integer page);


    /**
     * 获取粉丝数量
     * @param userId 用户id
     * @return Integer
     */
    Integer getAttentionMyCount(Integer userId);


    /**
     * 获取我关注的人的数量
     * @param userId 用户ID
     * @return Integer
     */
    Integer getMyAttentionCount(Integer userId);

}
