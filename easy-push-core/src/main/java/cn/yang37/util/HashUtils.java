package cn.yang37.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * @description:
 * @class: HashUtils
 * @author: yang37z@qq.com
 * @date: 2024/3/2 1:25
 * @version: 1.0
 */
public class HashUtils {

    public static final String SHA_256 = "SHA-256";

    public static String sha256HexLower(String source) throws Exception {
        MessageDigest md = MessageDigest.getInstance(SHA_256);
        byte[] d = md.digest(source.getBytes(StandardCharsets.UTF_8));
        return HexUtils.byteArr2HexLower(d);
    }

}