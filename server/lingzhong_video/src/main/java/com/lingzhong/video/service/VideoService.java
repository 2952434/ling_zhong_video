package com.lingzhong.video.service;


import com.lingzhong.video.bean.dto.VideoPublishDTO;
import com.lingzhong.video.bean.po.Video;
import com.lingzhong.video.bean.vo.VideoVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author ljx
 * @description 针对表【video】的数据库操作Service
 * @createDate 2023-10-27 20:30:22
 */
public interface VideoService {


    /**
     * 用户发布视频
     *
     * @param file            视频文件
     * @param videoPublishDTO 发布视频需要携带的参数
     * @return 是否成功
     */
    Boolean uploadVideo(MultipartFile file, VideoPublishDTO videoPublishDTO) throws Exception;


    /**
     * 获取视频信息
     *
     * @param page 第几页
     * @return List<VideoVo>
     */
    List<VideoVo> getVideo(Integer page);

    /**
     * 根据视频id获取视频信息
     */
    Video getVideoById(Integer videoIds);

    /**
     * 查询用户喜欢的视频信息
     */
    List<VideoVo> getUserLikeVideoList(Integer userId);

    /**
     * 查询用户收藏的视频信息
     */
    List<VideoVo> getUserCollectVideoList(Integer userId);


    /**
     * 根据用户ID查询该用户发表的视频
     *
     * @param userId 用户ID
     * @param page   页数
     * @param count  每页几条
     * @return List<VideoVo>
     */
    List<VideoVo> getUserVideoByUserId(Integer userId, Integer page, Integer count);


}
