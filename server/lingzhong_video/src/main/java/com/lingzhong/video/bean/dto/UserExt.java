package com.lingzhong.video.bean.dto;

import com.lingzhong.video.bean.po.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 李君祥
 * @Date: 2023/10/29 17:22
 * @Description: 用户认证拓展
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserExt extends User {
    /**
     * 用户权限
     */
    List<String> permissions = new ArrayList<>();
}
