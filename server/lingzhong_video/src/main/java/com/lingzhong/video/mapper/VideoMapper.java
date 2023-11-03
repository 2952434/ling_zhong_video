package com.lingzhong.video.mapper;

import com.lingzhong.video.bean.dto.VideoEsDTO;
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

    /**
     * 排除videoIds的视频
     * @param videoIds 视频ID集合
     * @return List<VideoVo>
     */
    List<VideoVo> getVideoByIp(@Param("videoIds") List<Integer> videoIds);

    /**
     * 查询出同步到es的数据
     * @return List<VideoEsDTO>
     */
    List<VideoEsDTO> getVideoEsBeans();

    /**
     * 根据视频id集合更新标记位为0
     * @param videoIds 视频id集合
     */
    void updateVideoSign(@Param("videoIds") List<Integer> videoIds);

    /**
     * 根据id获取视频信息
     * @param videoId 视频id
     * @return 视频信息
     */
    VideoVo getVideoVoById(@Param("videoId") Integer videoId);
}




