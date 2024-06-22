package cn.yang37.chain.node.sms.tencent.v3;

import cn.yang37.chain.node.adapter.MessageNodeAdapterSmsTencentV3;
import cn.yang37.constant.AppConstant;
import cn.yang37.constant.SmsTencentV3Constant;
import cn.yang37.entity.context.MessageContext;
import cn.yang37.entity.context.ThreadContext;
import cn.yang37.util.GsonUtils;
import cn.yang37.util.OgnlUtils;
import cn.zhxu.okhttps.HttpResult;
import cn.zhxu.okhttps.OkHttps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.cxf.common.util.UrlUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * @description:
 * @class: TencentSmsV3SendNode
 * @author: yang37z@qq.com
 * @date: 2024/3/2 21:32
 * @version: 1.0
 */
@Slf4j
public class SmsTencentV3SendNode extends MessageNodeAdapterSmsTencentV3 {

    @Override
    public MessageContext nodeSingleSend(MessageContext messageContext) throws Exception {

        // 获取参数
        String timestamp = ThreadContext.getContext(SmsTencentV3Constant.TIMESTAMP);
        String authorization = ThreadContext.getContext(SmsTencentV3Constant.AUTHORIZATION);
        String requestBody = ThreadContext.getContext(SmsTencentV3Constant.REQUEST_BODY);
        String url = ThreadContext.getContext(SmsTencentV3Constant.URL);
        String host = ThreadContext.getContext(SmsTencentV3Constant.HOST);
        String region = ThreadContext.getContext(SmsTencentV3Constant.REGION);

        // 构建请求头
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", authorization);
        headers.put("Content-Type", AppConstant.HTTP_HEADER_JSON);
        headers.put("Host", host);
        headers.put("X-TC-Action", SmsTencentV3Constant.ACTION);
        headers.put("X-TC-Timestamp", timestamp);
        headers.put("X-TC-Version", SmsTencentV3Constant.VERSION);
        headers.put("X-TC-Region", region);

        log.info("http url: {}", UrlUtils.urlDecode(url));
        log.info("http request body: {}", requestBody);
        HttpResult httpResult = OkHttps.sync(url)
                .addHeader(headers)
                .setBodyPara(requestBody)
                .bodyType(OkHttps.JSON)
                .post();

        if (ObjectUtils.isEmpty(httpResult)) {
            log.error("failed to send HTTP request! url: {}", url);
        }

        int status = httpResult.getStatus();
        HttpResult.Body body = httpResult.getBody().cache();
        log.info("http response body[{}]: {}", status, body.toString());

        // 判断发送状态
        Object code = OgnlUtils.getValue("Response.SendStatusSet.SendStatusSet[0].Code", GsonUtils.toMap(body.toString()));
        // 判断结果
        messageContext.setState(!Optional.ofNullable(code).isPresent());
        return messageContext;
    }

    @Override
    public List<MessageContext> nodeMultipleSend(List<MessageContext> messageContextList) {
        return null;
    }
}