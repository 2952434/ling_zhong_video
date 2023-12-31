package com.lingzhong.video.mapper;

import com.lingzhong.video.bean.po.VideoData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author ljx
 * @description 针对表【video_data】的数据库操作Mapper
 * @createDate 2023-10-27 21:36:13
 * @Entity com.lingzhong.video.bean.po.VideoData
 */
@Repository
public interface VideoDataMapper extends BaseMapper<VideoData> {

    /**
     * 跟新视频数据
     * @param filed 修改字段
     * @param videoId 视频Id
     * @param num 修改量
     * @return Integer
     */
    Integer updateVideoDataByFiled(@Param("filed") String filed, @Param("videoId") Integer videoId, @Param("num") Integer num);
}




