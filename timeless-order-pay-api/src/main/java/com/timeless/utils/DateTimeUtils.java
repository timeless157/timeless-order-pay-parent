package com.timeless.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
* @Description： 生成标准时间格式，类如: 2023-5-20 12:5:20
* @Date: 2023/5/29 19:46
* @Author: timeless
*/
public class DateTimeUtils {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String getCurrentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        return now.format(formatter);
    }

    public static String getCurrentDateTimePlusOneMinute(Long expireTime) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime plusOneMinute = now.plusMinutes(expireTime);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        return plusOneMinute.format(formatter);
    }
}