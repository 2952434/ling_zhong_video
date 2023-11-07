package com.lingzhong.video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lingzhong.video.bean.po.User;
import com.lingzhong.video.bean.po.UserAttention;
import com.lingzhong.video.bean.vo.AttentionUserVo;
import com.lingzhong.video.mapper.UserAttentionMapper;
import com.lingzhong.video.service.UserAttentionService;
import com.lingzhong.video.utils.FinalName;
import com.lingzhong.video.utils.LoginUser;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author ljx
 * @description 针对表【user_attention】的数据库操作Service实现
 * @createDate 2023-10-27 20:30:22
 */
@Service
public class UserAttentionServiceImpl implements UserAttentionService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private UserAttentionMapper userAttentionMapper;

    @Override
    public boolean attentionUserByUserId(Integer beUserId) {
        User user = LoginUser.getUser();
        LambdaQueryWrapper<UserAttention> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserAttention::getUserId, user.getUserId()).eq(UserAttention::getBeUserId, beUserId);
        List<UserAttention> userAttentions = userAttentionMapper.selectList(queryWrapper);
        if (userAttentions.size() > 0) {
            return false;
        }
        UserAttention userAttention = new UserAttention();
        userAttention.setUserId(user.getUserId());
        userAttention.setBeUserId(beUserId);
        userAttention.setAttentionDate(new Date());
        int insert = userAttentionMapper.insert(userAttention);
//        添加到Redis用于视频推送
        redisTemplate.opsForSet().add(FinalName.USER_ATTENTION_KEY + beUserId, user.getUserId());

        return insert > 0;
    }

    @Override
    public boolean deleteAttentionByUserId(Integer beUserId) {
        User user = LoginUser.getUser();
        LambdaQueryWrapper<UserAttention> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserAttention::getUserId, user.getUserId()).eq(UserAttention::getBeUserId, beUserId);
        int delete = userAttentionMapper.delete(queryWrapper);
//        删除推送
        redisTemplate.opsForSet().remove(FinalName.USER_ATTENTION_KEY + beUserId, user.getUserId());

        return delete > 0;
    }

    @Override
    public List<AttentionUserVo> getAttentionMyPeople(Integer page) {
        User user = LoginUser.getUser();
        Integer userId = user.getUserId();
        Integer count = 30;
        return userAttentionMapper.getAttentionMyPeople(userId, page * count, count);
    }

    @Override
    public List<AttentionUserVo> getMyAttentionPeople(Integer page) {
        User user = LoginUser.getUser();
        Integer userId = user.getUserId();
        Integer count = 30;
        return userAttentionMapper.getMyAttentionPeople(userId, page * count, count);

    }

    @Override
    public Integer getAttentionMyCount(Integer userId) {
        if (userId == null) {
            userId = LoginUser.getUser().getUserId();
        }
        LambdaQueryWrapper<UserAttention> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserAttention::getBeUserId, userId);
        return userAttentionMapper.selectCount(queryWrapper);
    }

    @Override
    public Integer getMyAttentionCount(Integer userId) {
        if (userId == null) {
            userId = LoginUser.getUser().getUserId();
        }
        LambdaQueryWrapper<UserAttention> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserAttention::getUserId, userId);
        return userAttentionMapper.selectCount(queryWrapper);
    }

    @Override
    public boolean judgeMyIsAttention(Integer beUserId) {
        User user = LoginUser.getUser();
        LambdaQueryWrapper<UserAttention> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserAttention::getUserId,user.getUserId()).eq(UserAttention::getBeUserId,beUserId);
        Integer count = userAttentionMapper.selectCount(queryWrapper);
        return count != 0;
    }
}




