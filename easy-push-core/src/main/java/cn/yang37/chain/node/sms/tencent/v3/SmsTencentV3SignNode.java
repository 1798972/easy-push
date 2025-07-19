package cn.yang37.chain.node.sms.tencent.v3;

import cn.yang37.chain.node.adapter.MessageNodeAdapterSmsTencentV3;
import cn.yang37.constant.AppConstant;
import cn.yang37.constant.SmsTencentV3Constant;
import cn.yang37.entity.context.MessageContext;
import cn.yang37.entity.context.ThreadContext;
import cn.yang37.util.HashUtils;
import cn.yang37.util.HexUtils;
import cn.yang37.util.SignUtils;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @description:
 * @class: TencentSmsConentNode
 * @author: yang37z@qq.com
 * @date: 2024/3/2 1:51
 * @version: 1.0
 */
@Slf4j
public class SmsTencentV3SignNode extends MessageNodeAdapterSmsTencentV3 {

    @Override
    public MessageContext nodeSingleSend(MessageContext messageContext) throws Exception {
        // 加载上下文
        String date = ThreadContext.getContext(SmsTencentV3Constant.DATE);
        String timestamp = ThreadContext.getContext(SmsTencentV3Constant.TIMESTAMP);
        String host = ThreadContext.getContext(SmsTencentV3Constant.HOST);
        String secretKey = ThreadContext.getContext(SmsTencentV3Constant.SECRET_KEY);
        String secretId = ThreadContext.getContext(SmsTencentV3Constant.SECRET_ID);

        // 构建canonicalHeaders
        String canonicalHeaders = String.format("%s%s\n%s%s\n", "content-type:", AppConstant.HTTP_HEADER_JSON, "host:", host);

        // 构建hashedRequestPayload
        String requestBody = JSON.toJSONString(
                messageContext.getMessage(), JSONWriter.Feature.PrettyFormat

        );
        String hashedRequestPayload = HashUtils.sha256HexLower(requestBody);
        log.debug("requestBody:\n{}", requestBody);
        log.debug("hashedRequestPayload:\n{}", hashedRequestPayload);

        // 构建canonicalRequest
        String canonicalRequest = String.format("%s\n%s\n%s\n%s\n%s\n%s",
                SmsTencentV3Constant.HTTP_REQUEST_METHOD,
                SmsTencentV3Constant.CANONICAL_URI,
                SmsTencentV3Constant.CANONICAL_QUERY_STRING,
                canonicalHeaders,
                SmsTencentV3Constant.SIGNED_HEADERS,
                hashedRequestPayload);
        log.debug("canonicalRequest:\n{}", canonicalRequest);

        // 计算签名
        String credentialScope = date + "/" + SmsTencentV3Constant.SERVICE + "/" + SmsTencentV3Constant.TC_3_REQUEST;
        String hashedCanonicalRequest = HashUtils.sha256HexLower(canonicalRequest);
        byte[] secretDate = SignUtils.hmac256((SmsTencentV3Constant.TC3 + secretKey).getBytes(StandardCharsets.UTF_8), date.getBytes(StandardCharsets.UTF_8));
        byte[] secretService = SignUtils.hmac256(secretDate, SmsTencentV3Constant.SERVICE.getBytes(StandardCharsets.UTF_8));
        byte[] signKey = SignUtils.hmac256(secretService, SmsTencentV3Constant.TC_3_REQUEST.getBytes(StandardCharsets.UTF_8));
        String signSource = String.format("%s\n%s\n%s\n%s", SmsTencentV3Constant.ALGORITHM, timestamp, credentialScope, hashedCanonicalRequest);
        String signature = HexUtils.byteArr2HexLower(SignUtils.hmac256(signKey, signSource.getBytes(StandardCharsets.UTF_8)));
        log.debug("signSource:\n{}", signSource);
        log.debug("signature:\n{}", signature);

        // 构建authorization字段
        String authorization = String.format("%s Credential=%s/%s, SignedHeaders=%s, Signature=%s",
                SmsTencentV3Constant.ALGORITHM,
                secretId,
                credentialScope,
                SmsTencentV3Constant.SIGNED_HEADERS,
                signature);
        log.debug("authorization:\n{}", authorization);

        // 放置参数
        ThreadContext.putContext(SmsTencentV3Constant.AUTHORIZATION, authorization);
        ThreadContext.putContext(SmsTencentV3Constant.REQUEST_BODY, requestBody);

        return messageContext;
    }

    @Override
    public List<MessageContext> nodeMultipleSend(List<MessageContext> messageContextList) {
        return null;
    }
}