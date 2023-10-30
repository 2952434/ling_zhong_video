package com.lingzhong.video.mapper;

import com.lingzhong.video.bean.po.Video;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingzhong.video.bean.vo.VideoVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author ljx
* @description 针对表【video】的数据库操作Mapper
* @createDate 2023-10-27 20:30:22
* @Entity com.lingzhong.video.bean.po.Video
*/
@Repository
public interface VideoMapper extends BaseMapper<Video> {

    /**
     * 分页查询视频
     * @param count 页
     * @return List<VideoVo>
     */
    List<VideoVo> getVideo(@Param("count") Integer count);

    /**
     * 查询用户喜欢的视频
     */
    List<VideoVo> selectLikeVideoByUserId(@Param("userId") Integer userId);

    /**
     * 查询用户收藏的视频
     */
    List<VideoVo> selectCollectVideoByUserId(@Param("userId") Integer userId);

    /**
     * 根据用户ID查询该用户发表的视频
     *
     * @param userId 用户ID
     * @param page   页数
     * @param count  每页几条
     * @return List<VideoVo>
     */
    List<VideoVo> getUserVideoByUserId(@Param("userId") Integer userId, @Param("page") Integer page, @Param("count") Integer count);
}




