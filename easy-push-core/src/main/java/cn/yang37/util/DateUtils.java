package cn.yang37.util;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @description:
 * @class: DateUtils
 * @author: yang37z@qq.com
 * @date: 2024/5/21 16:23
 * @version: 1.0
 */
public class DateUtils {

    /**
     * 获取ISO8601时间
     *
     * @return .
     */
    public static String getDateTimeIso8601() {
        // 获取当前本地时间
        ZonedDateTime nowLocal = ZonedDateTime.now();
        // 将本地时间转换为UTC时间
        ZonedDateTime nowUtc = nowLocal.withZoneSameInstant(ZoneId.of("UTC"));
        // 使用ISO_INSTANT格式化，并去掉毫秒部分
        return nowUtc.truncatedTo(java.time.temporal.ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_INSTANT);
    }
}