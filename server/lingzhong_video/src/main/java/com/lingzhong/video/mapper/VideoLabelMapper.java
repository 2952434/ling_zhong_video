package com.lingzhong.video.mapper;

import com.lingzhong.video.bean.po.VideoLabel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingzhong.video.bean.vo.VideoVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author ljx
* @description 针对表【video_label】的数据库操作Mapper
* @createDate 2023-10-27 20:30:22
* @Entity com.lingzhong.video.bean.po.VideoLabelDTO
*/
@Repository
public interface VideoLabelMapper extends BaseMapper<VideoLabel> {

    /**
     * 根据标签id查询视频
     * @param labelId 标签id
     * @param page 页数
     * @return List<VideoVo>
     */
    List<VideoVo> getVideoByLabelId(@Param("labelId") Integer labelId,@Param("page") Integer page);
}




