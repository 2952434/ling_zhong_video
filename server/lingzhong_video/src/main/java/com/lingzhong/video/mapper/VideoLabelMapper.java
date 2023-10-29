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
    @Select("select v.video_id, video_description, video_url, video_user_id, video_address, video_date, video_like_num, video_collect_num, video_comment_num,u.user_name,u.user_photo\n" +
            "from video_label l,video v,user u,video_data d\n" +
            "where l.label_id = #{labelId} and v.video_id = l.video_id and v.video_user_id = u.user_id and v.video_id = d.video_id order by v.video_date DESC limit #{page},10;")
    List<VideoVo> getVideoByLabelId(@Param("labelId") Integer labelId,@Param("page") Integer page);
}




