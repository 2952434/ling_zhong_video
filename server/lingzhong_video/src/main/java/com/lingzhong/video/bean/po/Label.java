package com.lingzhong.video.bean.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@TableName(value = "label")
@Data
@ApiModel
public class Label implements Serializable {

    /**
     * 标签id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "标签id")
    private Integer labelId;

    /**
     * 标签名
     */
    @ApiModelProperty(value = "标签名", required = true)
    private String labelName;

    /**
     * 添加标签的用户id（默认展示为-1）
     */
    @ApiModelProperty(value = "添加标签的用户id（默认展示为-1）")
    private Integer userId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}