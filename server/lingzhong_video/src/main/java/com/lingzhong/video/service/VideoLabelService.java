package com.lingzhong.video.service;

import com.lingzhong.video.bean.po.VideoLabel;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lingzhong.video.bean.vo.VideoVo;

import java.util.List;

/**
 * @author ljx
 * @description 针对表【video_label】的数据库操作Service
 * @createDate 2023-10-27 20:30:22
 */
public interface VideoLabelService {


    /**
     * 根据标签id查询分页查询视频
     *
     * @param labelId 标签id
     * @param page    页数
     * @return List<VideoVo>
     */
    List<VideoVo> getVideoByLabelId(Integer labelId, Integer page);


    /**
     * 根据视频ID查询标签
     *
     * @param videoId 视频ID
     * @return 标签ID集合
     */
    List<Integer> getLabelIdsByVideoId(Integer videoId);

}
