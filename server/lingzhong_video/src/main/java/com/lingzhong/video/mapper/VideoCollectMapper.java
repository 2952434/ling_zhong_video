package com.lingzhong.video.mapper;

import com.lingzhong.video.bean.po.VideoCollect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author ljx
 * @description 针对表【video_collect】的数据库操作Mapper
 * @createDate 2023-10-27 20:30:22
 * @Entity com.lingzhong.video.bean.po.VideoCollect
 */
@Repository
public interface VideoCollectMapper extends BaseMapper<VideoCollect> {
    /**
     * 新增视频收藏
     * @param videoCollect 视频收藏
     * @return Integer
     */
    Integer innerNewVideoCollect(@Param("videoCollect") VideoCollect videoCollect);
}




