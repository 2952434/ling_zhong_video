package com.lingzhong.video.bean.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @Author: 李君祥
 * @Date: 2023/11/3 22:07
 * @Description: ES用于视频搜索
 */
@Data
@Document(indexName = "lingzhong-video")
public class VideoEsDTO {

    @Id
    @Field(type = FieldType.Integer, name = "videoId")
    private Integer videoId;

    /**
     * 视频描述
     */
    @Field(analyzer = "ik_smart", searchAnalyzer = "ik_smart", type = FieldType.Text, name = "videoDescription")
    private String videoDescription;

    /**
     * 视频发表的地理位置
     */
    @Field(analyzer = "ik_smart", searchAnalyzer = "ik_smart", type = FieldType.Text, name = "videoAddress")
    private String videoAddress;

    /**
     * 用户主键
     */
    @Field(type = FieldType.Keyword, name = "userId")
    private Integer userId;

    /**
     * 用户名
     */
    @Field(analyzer = "ik_smart", searchAnalyzer = "ik_smart", type = FieldType.Text, name = "userName")
    private String userName;


}
