package cn.yang37.util;

import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @class: HttpUtils
 * @author: yang37z@qq.com
 * @date: 2024/6/22 22:26
 * @version: 1.0
 */
@Slf4j
public class HttpUtils {

    public static String formatSendUrl(String defUrl, String realUrl, String path) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(realUrl)) {
            return defUrl + path;
        }
        return realUrl + path;
    }
}
