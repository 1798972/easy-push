package cn.yang37.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description:
 * @class: ConfigUtils
 * @author: yang37z@qq.com
 * @date: 2023/5/15 2:05
 * @version: 1.0
 */
public class StringUtils {

    public static final Pattern HOST_PATTERN = Pattern.compile("^(?:https?://)?(?:www\\.)?([^/]+)");

    /**
     * 从URL中提取host
     *
     * @param url .
     * @return .
     */
    public static String parseHostFromUrl(String url) {
        return Optional.ofNullable(url)
                .map(HOST_PATTERN::matcher)
                .filter(Matcher::find)
                .map(m -> m.group(1))
                .orElse("");
    }

    /**
     * 规范化URI的方法
     *
     * @param uri 要编码的URI部分
     * @return 规范化后的URI
     * @throws UnsupportedEncodingException 如果字符编码不支持
     */
    public static String canonicalizeUri(String uri) throws UnsupportedEncodingException {
        // Use URLEncoder to encode the path using UTF-8 encoding
        String encodedPath = URLEncoder.encode(uri, "UTF-8");

        // Replace specific characters according to the rules
        encodedPath = encodedPath.replace("+", "%20");
        encodedPath = encodedPath.replace("*", "%2A");
        encodedPath = encodedPath.replace("%7E", "~");

        return encodedPath;
    }

    /**
     * 传入realStr,返回realStr+append
     * 如果realStr为空,则返回defStr+append
     */
    public static String formatUrl(String realStr, String defStr, String append) {
        if (org.apache.commons.lang3.StringUtils.isBlank(realStr)) {
            return defStr + append;
        }
        return realStr + append;
    }

}