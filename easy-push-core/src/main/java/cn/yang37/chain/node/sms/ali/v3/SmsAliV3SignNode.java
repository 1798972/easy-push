package cn.yang37.chain.node.sms.ali.v3;

import cn.yang37.chain.node.adapter.MessageNodeAdapterSmsAli;
import cn.yang37.constant.SmsAliV3Constant;
import cn.yang37.entity.context.MessageContext;
import cn.yang37.entity.context.ThreadContext;
import cn.yang37.entity.message.SmsAliV3Message;
import cn.yang37.util.GsonUtils;
import cn.yang37.util.HashUtils;
import cn.yang37.util.SignUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @description:
 * @class: AliSmsParamNode
 * @author: yang37z@qq.com
 * @date: 2024/5/21 16:03
 * @version: 1.0
 */
@Slf4j
public class SmsAliV3SignNode extends MessageNodeAdapterSmsAli {

    private final static String SPLIT = ";";

    @Override
    public MessageContext nodeSingleSend(MessageContext messageContext) throws Exception {
        SmsAliV3Message message = (SmsAliV3Message) messageContext.getMessage();
        // 请求体
        String requestPayload = GsonUtils.toJson(message);
        String hashedRequestPayload = HashUtils.sha256HexLower(requestPayload);
        log.debug("requestPayload: {},hashedRequestPayload: {}", requestPayload, hashedRequestPayload);

        Map<String, String> paramMap = GsonUtils.toMap(requestPayload);
        // 填充信息
        ThreadContext.putContext(SmsAliV3Constant.AUTHORIZATION, parseAuthorization(paramMap, hashedRequestPayload));
        ThreadContext.putContext(SmsAliV3Constant.HTTP_REQUEST_PARAM, paramMap);
        ThreadContext.putContext(SmsAliV3Constant.X_ACS_CONTENT_SHA_256, hashedRequestPayload);

        return messageContext;
    }

    @Override
    public List<MessageContext> nodeMultipleSend(List<MessageContext> messageContextList) {
        return null;
    }

    /**
     * 构造构造规范化请求时,HashedRequestPayload需要转换格式.
     *
     * @param paramMap .
     * @return .
     */
    private static String map2kv(Map<String, String> paramMap) {
        Map<String, String> treeMap = new TreeMap<>(paramMap);
        String collect = treeMap.entrySet()
                .stream()
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining("&"));
        log.debug("paramMap: {},change: {}", GsonUtils.toJson(treeMap), collect);
        return collect;
    }

    /**
     * 规范化请求头
     *
     * @return .
     */
    private static String parseCanonicalizedHeaders(String hashedRequestPayload) {
        // 规范化请求头
        String canonicalizedHeaders = "{{host}}\n" +
                "{{x-acs-action}}\n" +
                "{{x-acs-content-sha256}}\n" +
                "{{x-acs-date}}\n" +
                "{{x-acs-signature-nonce}}\n" +
                "{{x-acs-version}}\n";

        // 执行替换
        canonicalizedHeaders = canonicalizedHeaders
                .replace("{{host}}", formatHeaderValue(SmsAliV3Constant.HOST, ThreadContext.getContext(SmsAliV3Constant.HOST)))
                .replace("{{x-acs-action}}", formatHeaderValue(SmsAliV3Constant.X_ACS_ACTION, ThreadContext.getContext(SmsAliV3Constant.X_ACS_ACTION)))
                .replace("{{x-acs-content-sha256}}", formatHeaderValue(SmsAliV3Constant.X_ACS_CONTENT_SHA_256, hashedRequestPayload))
                .replace("{{x-acs-date}}", formatHeaderValue(SmsAliV3Constant.X_ACS_DATE, ThreadContext.getContext(SmsAliV3Constant.X_ACS_DATE)))
                .replace("{{x-acs-signature-nonce}}", formatHeaderValue(SmsAliV3Constant.X_ACS_SIGNATURE_NONCE, ThreadContext.getContext(SmsAliV3Constant.X_ACS_SIGNATURE_NONCE)))
                .replace("{{x-acs-version}}", formatHeaderValue(SmsAliV3Constant.X_ACS_VERSION, ThreadContext.getContext(SmsAliV3Constant.X_ACS_VERSION)));
        log.debug("规范化请求头canonicalizedHeaders:\n{}", canonicalizedHeaders);

        return canonicalizedHeaders;
    }

    /**
     * 已签名消息头列表
     *
     * @return .
     */
    private static String parseSignedHeaders() {
        String signedHeaders = SmsAliV3Constant.HOST + SPLIT +
                SmsAliV3Constant.X_ACS_ACTION + SPLIT +
                SmsAliV3Constant.X_ACS_CONTENT_SHA_256 + SPLIT +
                SmsAliV3Constant.X_ACS_DATE + SPLIT +
                SmsAliV3Constant.X_ACS_SIGNATURE_NONCE + SPLIT +
                SmsAliV3Constant.X_ACS_VERSION;

        log.debug("已签名消息头列表signedHeaders:\n{}", signedHeaders);
        return signedHeaders;
    }

    /**
     * 规范化请求
     *
     * @param paramMap             .
     * @param hashedRequestPayload .
     * @return .
     */
    private static String parseCanonicalRequest(Map<String, String> paramMap, String hashedRequestPayload) throws Exception {
        // 构造规范化请求
        String canonicalRequest = "{{HTTPRequestMethod}}\n" +
                "{{CanonicalURI}}\n" +
                "{{CanonicalQueryString}}\n" +
                "{{CanonicalHeaders}}\n" +
                "{{SignedHeaders}}\n" +
                "{{HashedRequestPayload}}";

        // 经过验证,这里不是json,而是map拉平
        String changedHashedRequestPayload = HashUtils.sha256HexLower(map2kv(paramMap));

        // 构建标准请求
        canonicalRequest = canonicalRequest.replace("{{HTTPRequestMethod}}", SmsAliV3Constant.HTTP_REQUEST_METHOD)
                .replace("{{CanonicalURI}}", SmsAliV3Constant.CANONICAL_URI)
                .replace("{{CanonicalQueryString}}", "")
                .replace("{{CanonicalHeaders}}", parseCanonicalizedHeaders(hashedRequestPayload))
                .replace("{{SignedHeaders}}", parseSignedHeaders())
                .replace("{{HashedRequestPayload}}", changedHashedRequestPayload);

        log.debug("构造规范化请求canonicalRequest:\n{}", canonicalRequest);
        return canonicalRequest;
    }

    /**
     * 计算签名
     *
     * @param canonicalRequest .
     * @return .
     * @throws Exception .
     */
    private String doSign(String canonicalRequest) throws Exception {
        String source2Sign = String.format("%s\n%s", SmsAliV3Constant.ACS_3_HMAC_SHA_256, HashUtils.sha256HexLower(canonicalRequest));
        log.debug("signSource:\n{}", source2Sign);

        String signAliSmsV3 = SignUtils.calculateSignAliSmsV3(configProperties.getAccessKeySecret(), source2Sign);
        log.debug("signAliSmsV3: {}", signAliSmsV3);

        return signAliSmsV3;
    }

    /**
     * Authorization
     *
     * @param paramMap             .
     * @param hashedRequestPayload .
     * @return .
     * @throws Exception .
     */
    private String parseAuthorization(Map<String, String> paramMap, String hashedRequestPayload) throws Exception {
        // Authorization:<SignatureAlgorithm> Credential=<AccessKeyId>,SignedHeaders=<SignedHeaders>,Signature=<Signature>
        String authorization = "{{SignatureAlgorithm}} Credential={{AccessKeyId}},SignedHeaders={{SignedHeaders}},Signature={{Signature}}";

        // 执行替换
        authorization = authorization.replace("{{SignatureAlgorithm}}", SmsAliV3Constant.ACS_3_HMAC_SHA_256)
                .replace("{{AccessKeyId}}", configProperties.getAccessKeyId())
                .replace("{{SignedHeaders}}", parseSignedHeaders())
                .replace("{{Signature}}", doSign(parseCanonicalRequest(paramMap, hashedRequestPayload)));

        log.debug("authorization:\n{}", authorization);
        return authorization;
    }

    /**
     * 格式化请求头
     *
     * @param header .
     * @param value  .
     * @return k:v
     */
    private static String formatHeaderValue(String header, String value) {
        return header.toLowerCase() + ":" + value.trim();
    }

    /**
     * 规范化查询字符串
     *
     * @param paramMap .
     * @return .
     */
    private static String parseCanonicalQueryString(Map<String, String> paramMap) {
        Map<String, String> sortedParams = new TreeMap<>(paramMap);
        String canonicalQueryString = sortedParams.entrySet().stream()
                .map(entry -> {
                    try {
                        String encodedName = encode(entry.getKey());
                        String encodedValue = encode(entry.getValue());
                        return encodedName + "=" + encodedValue;
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException("Encoding not supported", e);
                    }
                }).collect(Collectors.joining("&"));
        log.debug("规范化查询字符串canonicalQueryString: {}", canonicalQueryString);
        return canonicalQueryString;
    }

    private static String encode(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, StandardCharsets.UTF_8.toString())
                .replace("+", "%20")
                .replace("*", "%2A")
                .replace("%7E", "~");
    }
}
