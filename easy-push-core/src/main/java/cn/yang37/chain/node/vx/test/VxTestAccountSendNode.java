package cn.yang37.chain.node.vx.test;

import cn.yang37.chain.node.adapter.MessageNodeAdapterVxTestAccountMessage;
import cn.yang37.constant.VxTestAccountConstant;
import cn.yang37.entity.context.MessageContext;
import cn.yang37.entity.context.ThreadContext;
import cn.yang37.entity.message.impl.VxTestAccountMessage;
import cn.yang37.util.HttpUtils;
import cn.yang37.util.JsonUtils;
import cn.zhxu.okhttps.HttpResult;
import cn.zhxu.okhttps.OkHttps;
import cn.zhxu.okhttps.SHttpTask;
import lombok.extern.slf4j.Slf4j;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

/**
 * @description:
 * @class: GetAccessTokenNode
 * @author: yang37z@qq.com
 * @date: 2024/7/12 16:54
 * @version: 1.0
 */
@Slf4j
public class VxTestAccountSendNode extends MessageNodeAdapterVxTestAccountMessage {

    @Override
    public MessageContext nodeSingleSend(MessageContext messageContext) throws Exception {
        VxTestAccountMessage vxTestAccountMessage = (VxTestAccountMessage) messageContext.getMessage();
        String accessToken = ThreadContext.getContext(VxTestAccountConstant.GetAccessToken.ACCESS_TOKEN, String.class);
        String reqData = JsonUtils.toSnakeCaseJsonString(vxTestAccountMessage);

        // 构建请求
        String sendUrl = HttpUtils.formatSendUrl(VxTestAccountConstant.URL, sceneConfig().getBaseUrl(), VxTestAccountConstant.PATH);
        SHttpTask httpTask = OkHttps.sync(sendUrl)
                .addUrlPara(VxTestAccountConstant.GetAccessToken.ACCESS_TOKEN, accessToken)
                .setBodyPara(reqData)
                .bodyType(OkHttps.JSON);

        log.info("http url: {}", URLDecoder.decode(httpTask.getUrl(), StandardCharsets.UTF_8.name()));
        log.info("http request body: {}", reqData);

        HttpResult httpResult = httpTask.post();

        messageContext.setResponseCode(httpResult.getStatus());
        HttpResult.Body body = httpResult.getBody().cache();
        messageContext.setResponse(body.toString());

        if (httpResult.isSuccessful()) {
            Integer errcode = JsonUtils.getValue("$.errcode", body, Integer.class);
            messageContext.setState(Objects.equals(errcode, 0));
        }

        return messageContext;
    }

    @Override
    public List<MessageContext> nodeMultipleSend(List<MessageContext> messageContextList) {
        return null;
    }
}