package com.lingzhong.video.service;


import com.lingzhong.video.bean.dto.VideoPublishDTO;
import com.lingzhong.video.bean.po.Video;
import com.lingzhong.video.bean.vo.RespBean;
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
     * @throws Exception 文件上传异常
     */
    Boolean uploadVideo(MultipartFile file, VideoPublishDTO videoPublishDTO) throws Exception;

    /**
     * 根据视频ID删除视频
     *
     * @param videoId 视频ID
     * @return 是否删除成功
     */
    RespBean<String> deleteVideoById(Integer videoId);


    /**
     * 获取视频信息
     *
     * @param page 第几页
     * @return List<VideoVo>
     */
    List<VideoVo> getVideo(Integer page);


    /**
     * 根据用户IP获取视频
     *
     * @param userIp 用户IP
     * @return List<VideoVo>
     */
    List<VideoVo> getVideoByIp(String userIp);


    /**
     * 用户登录后用于推荐视频
     *
     * @param count 每次推荐视频的个数
     * @return List<VideoVo>
     */
    List<VideoVo> recommendVideo(Integer count);


    /**
     * 根据视频id获取视频信息
     *
     * @param videoIds 视频Id
     * @return Video
     */
    Video getVideoById(Integer videoIds);

    /**
     * 查询用户喜欢的视频信息
     *
     * @param userId 用户Id
     * @return List<VideoVo>
     */
    List<VideoVo> getUserLikeVideoList(Integer userId);

    /**
     * 查询用户收藏的视频信息
     *
     * @param userId 用户Id
     * @return List<VideoVo>
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


    /**
     * 更新Es
     */
    void updateVideoEs();


    /**
     * 根据内容从ES中分页查询并高亮显示
     *
     * @param content 内容
     * @param page    第几页
     * @param count   每页条数
     * @return List<VideoVo>
     */
    List<VideoVo> getVideoByEsAndHighLight(String content, Integer page, Integer count);


    /**
     * 根据视频ID删除ES中的视频数据
     *
     * @param videoId 视频ID
     */
    void deleteVideoEsById(Integer videoId);


}
