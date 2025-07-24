package cn.yang37.easypush.core.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @description:
 * @class: SignUtils
 * @author: yang37z@qq.com
 * @date: 2023/9/5 22:23
 * @version: 1.0
 */
public class SignUtils {

    public static final String HMAC_SHA_256 = "HmacSHA256";

    /**
     * HMAC_SHA_256
     *
     * @param key .
     * @param msg .
     * @return .
     * @throws NoSuchAlgorithmException .
     * @throws InvalidKeyException      .
     */
    public static byte[] hmac256(String key, String msg) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance(HMAC_SHA_256);
        mac.init(new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), mac.getAlgorithm()));
        return mac.doFinal(msg.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * HMAC_SHA_256
     *
     * @param key .
     * @param msg .
     * @return .
     * @throws NoSuchAlgorithmException .
     * @throws InvalidKeyException      .
     */
    public static byte[] hmac256(byte[] key, byte[] msg) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance(HMAC_SHA_256);
        mac.init(new SecretKeySpec(key, mac.getAlgorithm()));
        return mac.doFinal(msg);
    }

    /**
     * 计算钉钉签名
     */
    public static String calculateSignDing(String secret, String timestamp) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        final String source = timestamp + "\n" + secret;
        byte[] hmac256 = hmac256(secret, source);
        return URLEncoder.encode(HexUtils.byteArr2Base64(hmac256));
    }

    /**
     * 计算腾讯云短信V3签名
     */
    public static String calculateSignTencentSmsV3(String service, String secretKey, String date, String algorithm, String timestamp, String credentialScope, String hashedCanonicalRequest) throws NoSuchAlgorithmException, InvalidKeyException {
        final String tc3Request = "tc3_request";
        final String tc3 = "TC3";

        byte[] secretDate = hmac256((tc3 + secretKey), date);
        byte[] secretService = hmac256(secretDate, service.getBytes(StandardCharsets.UTF_8));
        byte[] secretSigning = hmac256(secretService, tc3Request.getBytes(StandardCharsets.UTF_8));
        String source = algorithm + "\n" + timestamp + "\n" + credentialScope + "\n" + hashedCanonicalRequest;
        return HexUtils.byteArr2HexLower(hmac256(secretSigning, source.getBytes(StandardCharsets.UTF_8)));
    }


    /**
     * 计算阿里云短信签名
     */
    public static String calculateSignAliSmsV3(String secret, String data) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        byte[] hmac256 = hmac256(secret, data);
        return HexUtils.byteArr2HexLower(hmac256);
    }
}