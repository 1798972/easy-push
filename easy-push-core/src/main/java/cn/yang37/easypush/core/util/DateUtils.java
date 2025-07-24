package cn.yang37.easypush.core.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @description:
 * @class: DateUtils
 * @author: yang37z@qq.com
 * @date: 2024/5/21 16:23
 * @version: 1.0
 */
public class DateUtils {

    private static final DateTimeFormatter YYYY_MM_DD_HH_MM_SS_SSS = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS");

    private static final DateTimeFormatter YYYY_MM_DD = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final DateTimeFormatter YYYY_MM_DD_UTC = DateTimeFormatter.ofPattern("yyyy/MM/dd").withZone(ZoneOffset.UTC);

    /**
     * 当前本地时间格式化为 yyyy/MM/dd HH:mm:ss.SSS
     */
    public static String nowDateTimeMillis() {
        return LocalDateTime.now().format(YYYY_MM_DD_HH_MM_SS_SSS);
    }

    /**
     * 时间戳（秒/毫秒自动适配）格式化为 yyyy/MM/dd HH:mm:ss.SSS（本地时区）
     */
    public static String formatMillisToLocal(long timestamp) {
        LocalDateTime dt = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(adaptMillis(timestamp)), ZoneId.systemDefault());
        return dt.format(YYYY_MM_DD_HH_MM_SS_SSS);
    }

    /**
     * 时间戳（秒/毫秒自动适配）格式化为 yyyy-MM-dd（本地时区）
     */
    public static String formatMillisToLocalDate(long timestamp) {
        LocalDateTime dt = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(adaptMillis(timestamp)), ZoneId.systemDefault());
        return dt.format(YYYY_MM_DD);
    }

    /**
     * 时间戳（秒/毫秒自动适配）格式化为 yyyy/MM/dd（UTC时区）
     */
    public static String formatMillisToUtcDate(long timestamp) {
        Instant instant = Instant.ofEpochMilli(adaptMillis(timestamp));
        return YYYY_MM_DD_UTC.format(instant);
    }

    /**
     * 获取ISO8601时间（到秒）
     */
    public static String getDateTimeIso8601() {
        // 获取当前UTC时间，去掉毫秒
        return Instant.now().truncatedTo(java.time.temporal.ChronoUnit.SECONDS).toString();
    }

    /**
     * 自动判断秒/毫秒级时间戳
     */
    private static long adaptMillis(long timestamp) {
        // 若传的是10位秒级时间戳，自动转为毫秒
        return timestamp < 1_000_000_000_000L ? timestamp * 1000 : timestamp;
    }
}