package cn.yang37.chain.node.sms.tencent.v3;

import cn.yang37.chain.node.adapter.MessageNodeAdapterSmsTencentV3;
import cn.yang37.constant.AppConstant;
import cn.yang37.constant.SmsTencentV3Constant;
import cn.yang37.entity.context.MessageContext;
import cn.yang37.entity.context.ThreadContext;
import cn.zhxu.okhttps.HttpResult;
import cn.zhxu.okhttps.OkHttps;
import com.alibaba.fastjson2.JSONPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class SmsTencentV3SendNode extends MessageNodeAdapterSmsTencentV3 {

    @Override
    public MessageContext nodeSingleSend(MessageContext messageContext) throws Exception {
        // 参数准备
        String url = ThreadContext.getContext(SmsTencentV3Constant.URL);
        String requestBody = ThreadContext.getContext(SmsTencentV3Constant.REQUEST_BODY);

        // 校验参数
        if (ObjectUtils.anyNull(url, requestBody)) {
            log.error("必需参数缺失，url 或 requestBody 为空！");
            messageContext.setState(false);
            return messageContext;
        }

        // 构建请求头
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", ThreadContext.getContext(SmsTencentV3Constant.AUTHORIZATION));
        headers.put("Content-Type", AppConstant.HTTP_HEADER_JSON);
        headers.put("Host", ThreadContext.getContext(SmsTencentV3Constant.HOST));
        headers.put("X-TC-Action", SmsTencentV3Constant.ACTION);
        headers.put("X-TC-Timestamp", ThreadContext.getContext(SmsTencentV3Constant.TIMESTAMP));
        headers.put("X-TC-Version", SmsTencentV3Constant.VERSION);
        headers.put("X-TC-Region", ThreadContext.getContext(SmsTencentV3Constant.REGION));

        // 日志
        String decodedUrl = URLDecoder.decode(url, StandardCharsets.UTF_8.name());
        log.info("Http url: {}", decodedUrl);
        log.info("Http request body: {}", requestBody);

        // 发送请求
        HttpResult httpResult = null;
        try {
            httpResult = OkHttps.sync(url)
                    .addHeader(headers)
                    .setBodyPara(requestBody)
                    .bodyType(OkHttps.JSON)
                    .post();
        } catch (Exception e) {
            log.error("HTTP请求异常: {}", e.getMessage(), e);
            messageContext.setState(false);
            return messageContext;
        }

        if (httpResult == null) {
            log.error("HttpResult为空，发送请求失败！url: {}", url);
            messageContext.setState(false);
            return messageContext;
        }

        int status = httpResult.getStatus();
        HttpResult.Body body = httpResult.getBody() != null ? httpResult.getBody().cache() : null;
        log.info("Http response body [{}]: {}", status, body);

        // 判断发送状态（注意 JSONPath 路径要与实际响应一致）
        Object code = null;
        if (body != null) {
            code = JSONPath.eval(body, "$.Response.SendStatusSet[0].Code");
        }
        boolean success = code != null && "Ok".equalsIgnoreCase(code.toString());
        messageContext.setState(success);

        if (!success) {
            log.warn("短信发送失败，code: {}, body: {}", code, body);
        }

        return messageContext;
    }

    @Override
    public List<MessageContext> nodeMultipleSend(List<MessageContext> messageContextList) {
        // 推荐这里写批量逻辑，如有需要
        return null;
    }
}
