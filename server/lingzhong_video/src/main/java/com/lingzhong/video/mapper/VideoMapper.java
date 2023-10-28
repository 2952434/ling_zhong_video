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
    @Select("select v.video_id, video_description, video_url, video_user_id, video_address, video_date, video_like_num, video_collect_num, video_comment_num,u.user_name,u.user_photo\n" +
            "from video v,video_data d,user u\n" +
            "where v.video_id = d.video_id and v.video_user_id = u.user_id limit #{count},10;")
    List<VideoVo> getVideo(@Param("count") Integer count);
}




