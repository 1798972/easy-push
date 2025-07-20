package cn.yang37.chain.node.sms.ali.v3;

import cn.yang37.chain.node.adapter.MessageNodeAdapterSmsAli;
import cn.yang37.constant.SmsAliV3Constant;
import cn.yang37.entity.context.MessageContext;
import cn.yang37.entity.context.ThreadContext;
import cn.zhxu.okhttps.HttpResult;
import cn.zhxu.okhttps.OkHttps;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONPath;
import lombok.extern.slf4j.Slf4j;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @class: AliSmsParamNode
 * @author: yang37z@qq.com
 * @date: 2024/5/21 16:03
 * @version: 1.0
 */
@Slf4j
public class SmsAliV3SendNode extends MessageNodeAdapterSmsAli {

    @Override
    public MessageContext nodeSingleSend(MessageContext messageContext) throws Exception {
        String authorization = ThreadContext.getContext(SmsAliV3Constant.AUTHORIZATION);
        String url = sceneConfig().getBaseUrl();
        String host = ThreadContext.getContext(SmsAliV3Constant.HOST);
        String xAcsAction = ThreadContext.getContext(SmsAliV3Constant.X_ACS_ACTION);
        String xAcsDate = ThreadContext.getContext(SmsAliV3Constant.X_ACS_DATE);
        String xAcsContentSha256 = ThreadContext.getContext(SmsAliV3Constant.X_ACS_CONTENT_SHA_256);
        String xAcsSignatureNonce = ThreadContext.getContext(SmsAliV3Constant.X_ACS_SIGNATURE_NONCE);
        String xAcsVersion = ThreadContext.getContext(SmsAliV3Constant.X_ACS_VERSION);
        Map<String, String> requestParam = ThreadContext.getContext(SmsAliV3Constant.HTTP_REQUEST_PARAM);

        // 构建请求头
        HashMap<String, String> headerList = new HashMap<>();
        headerList.put(SmsAliV3Constant.HOST, host);
        headerList.put(SmsAliV3Constant.X_ACS_ACTION, xAcsAction);
        headerList.put(SmsAliV3Constant.X_ACS_CONTENT_SHA_256, xAcsContentSha256);
        headerList.put(SmsAliV3Constant.X_ACS_DATE, xAcsDate);
        headerList.put(SmsAliV3Constant.X_ACS_SIGNATURE_NONCE, xAcsSignatureNonce);
        headerList.put(SmsAliV3Constant.X_ACS_VERSION, xAcsVersion);
        headerList.put(SmsAliV3Constant.AUTHORIZATION, authorization);

        log.info("http url: {}", URLDecoder.decode(url, StandardCharsets.UTF_8.name()));
        log.info("http request body: {}", requestParam);

        HttpResult httpResult = OkHttps
                .sync(url)
                .addHeader(headerList)
                .setBodyPara(requestParam)
                .bodyType(OkHttps.FORM)
                .post();


        // 判断结果
        int status = httpResult.getStatus();
        String responseBodyStr = httpResult.getBody().cache().toString();
        log.info("http response body[{}]: {}", status, responseBodyStr);

        // 填充数据
        messageContext.setResponseCode(status);
        messageContext.setResponse(responseBodyStr);

        // 构建返回
        if (200 == status) {
            String code = String.valueOf(JSONPath.eval(JSON.parse(responseBodyStr), "$.Code"));
            messageContext.setState(SmsAliV3Constant.Code.OK.equals(code));
        } else {
            messageContext.setState(false);
        }

        return messageContext;
    }

    @Override
    public List<MessageContext> nodeMultipleSend(List<MessageContext> messageContextList) {
        return null;
    }
}
