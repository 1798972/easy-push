package cn.yang37.easypush.core.util;

import org.apache.commons.codec.binary.Hex;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Locale;

/**
 * @description: 格式转换Utils, 默认UTF-8
 * @class: HexUtils
 * @author: yang37z@qq.com
 * @date: 2022/7/1 18:35
 * @version: 2.0 (commons-codec)
 */
public class HexUtils {

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /** ==========================  byte ================================ **/
    public static String byteArr2Str(byte[] byteArr) {
        return new String(byteArr, DEFAULT_CHARSET);
    }

    public static String byteArr2Base64(byte[] byteArr) {
        return Base64.getEncoder().encodeToString(byteArr);
    }

    public static String byteArr2Hex(byte[] byteArr) {
        return Hex.encodeHexString(byteArr).toUpperCase(Locale.ROOT);
    }

    public static String byteArr2HexLower(byte[] byteArr) {
        return Hex.encodeHexString(byteArr).toLowerCase(Locale.ROOT);
    }

    /** ==========================  str ================================ **/
    public static byte[] str2ByteArr(String str) {
        return str.getBytes(DEFAULT_CHARSET);
    }

    public static String str2Base64(String str) {
        return byteArr2Base64(str2ByteArr(str));
    }

    public static String str2Hex(String str) {
        return byteArr2Hex(str2ByteArr(str));
    }

    public static String str2HexLower(String str) {
        return byteArr2HexLower(str2ByteArr(str));
    }

    /** ==========================  hex ================================ **/
    public static byte[] hex2ByteArr(String hex) {
        try {
            return Hex.decodeHex(hex);
        } catch (Exception e) {
            throw new IllegalArgumentException("Hex decode error: " + hex, e);
        }
    }

    public static String hex2Base64(String hex) {
        return byteArr2Base64(hex2ByteArr(hex));
    }

    public static String hex2Str(String hex) {
        return byteArr2Str(hex2ByteArr(hex));
    }

    /** ==========================  base64 ================================ **/
    public static byte[] base642ByteArr(String base64) {
        return Base64.getDecoder().decode(base64);
    }

    public static String base642Str(String base64) {
        return byteArr2Str(base642ByteArr(base64));
    }

    public static String base642Hex(String base64) {
        return byteArr2Hex(base642ByteArr(base64));
    }

    public static String base642HexLower(String base64) {
        return byteArr2HexLower(base642ByteArr(base64));
    }

}
