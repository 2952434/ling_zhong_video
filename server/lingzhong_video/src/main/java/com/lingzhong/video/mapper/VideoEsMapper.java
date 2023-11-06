package com.lingzhong.video.mapper;

import com.lingzhong.video.bean.dto.VideoEsDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: 李君祥
 * @Date: 2023/11/3 23:01
 * @Description: es映射
 */
@Repository
public interface VideoEsMapper extends ElasticsearchRepository<VideoEsDTO, Integer> {
}
