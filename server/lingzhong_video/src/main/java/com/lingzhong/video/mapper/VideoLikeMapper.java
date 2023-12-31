package com.lingzhong.video.mapper;

import com.lingzhong.video.bean.po.VideoLike;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author ljx
 * @description 针对表【video_like】的数据库操作Mapper
 * @createDate 2023-10-27 20:30:22
 * @Entity com.lingzhong.video.bean.po.VideoLike
 */
@Repository
public interface VideoLikeMapper extends BaseMapper<VideoLike> {

    /**
     * 添加点赞记录
     * @param videoLike 视频点赞对象
     * @return Integer
     */
    Integer innerNewVideoLike(@Param("videoLike") VideoLike videoLike);
}




