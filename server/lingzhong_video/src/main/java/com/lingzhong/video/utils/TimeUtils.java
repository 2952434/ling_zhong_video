package com.lingzhong.video.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @Author: 李君祥
 * @Date: 2023/10/28 23:42
 * @Description: 时间工具类
 */
public class TimeUtils {

    /**
     * 根据传入的时间格式获取当前时间
     *
     * @param pattern 例如：yyyy/MM/dd
     * @return String
     */
    public static String getNowDateString(String pattern) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return currentDate.format(formatter);
    }

}
