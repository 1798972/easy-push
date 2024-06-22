package cn.yang37.util;

import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Locale;

/**
 * @description: 格式转换Utils, 默认UTF-8
 * @class: HexUtils
 * @author: yang37z@qq.com
 * @date: 2022/7/1 18:35
 * @version: 1.0
 */
public class HexUtils {

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /**
     * ==========================  byte ================================
     **/
    public static String byteArr2Str(byte[] byteArr) {
        return new String(byteArr, DEFAULT_CHARSET);
    }

    public static String byteArr2Base64(byte[] byteArr) {
        return new String(Base64.getEncoder().encode(byteArr));
    }

    public static String byteArr2Hex(byte[] byteArr) {
        return Hex.toHexString(byteArr).toUpperCase(Locale.ROOT);
    }

    public static String byteArr2HexLower(byte[] byteArr) {
        return Hex.toHexString(byteArr).toLowerCase(Locale.ROOT);
    }

    /**
     * ==========================  str ================================
     **/
    public static byte[] str2ByteArr(String str) {
        return str.getBytes(DEFAULT_CHARSET);
    }

    public static String str2Base64(String str) {
        return byteArr2Base64(str2ByteArr(str));
    }

    public static String str2Hex(String str) {
        return byteArr2Hex(str2ByteArr(str)).toUpperCase(Locale.ROOT);
    }

    public static String str2HexLower(String str) {
        return byteArr2HexLower(str2ByteArr(str));
    }

    /**
     * ==========================  hex ================================
     **/
    public static byte[] hex2ByteArr(String hex) {
        return Hex.decode(str2ByteArr(hex));
    }

    public static String hex2Base64(String hex) {
        return byteArr2Base64(hex2ByteArr(hex));
    }

    public static String hex2Str(String hex) {
        return byteArr2Str(hex2ByteArr(hex));
    }

    /**
     * ==========================  base64 ================================
     **/
    public static byte[] base642ByteArr(String base64) {
        return Base64.getDecoder().decode(str2ByteArr(base64));
    }

    public static String base642Str(String base64) {
        return byteArr2Str(base642ByteArr(base64));
    }

    public static String base642Hex(String base64) {
        return byteArr2Hex(base642ByteArr(base64));
    }

    public static String base642HexLowe(String base64) {
        return byteArr2HexLower(base642ByteArr(base64));
    }

}
