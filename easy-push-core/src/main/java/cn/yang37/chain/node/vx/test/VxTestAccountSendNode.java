package cn.yang37.chain.node.vx.test;

import cn.yang37.chain.node.adapter.MessageNodeAdapterVxTestAccountMessage;
import cn.yang37.constant.VxTestAccountConstant;
import cn.yang37.entity.context.MessageContext;
import cn.yang37.entity.context.ThreadContext;
import cn.yang37.entity.message.VxTestAccountMessage;
import cn.yang37.util.GsonUtils;
import cn.yang37.util.HttpUtils;
import cn.zhxu.okhttps.HttpResult;
import cn.zhxu.okhttps.OkHttps;
import cn.zhxu.okhttps.SHttpTask;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.common.util.UrlUtils;

import java.util.List;

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
        String reqData = GsonUtils.toJsonSnakeCase(vxTestAccountMessage);

        // 请求URL
        String baseUrl = configProperties.getBaseUrl();
        if (StringUtils.isEmpty(baseUrl)) {
            baseUrl = VxTestAccountConstant.URL;
        }

        // 构建请求
        SHttpTask httpTask = OkHttps.sync(baseUrl)
                .addUrlPara(VxTestAccountConstant.GetAccessToken.ACCESS_TOKEN, accessToken)
                .setBodyPara(reqData)
                .bodyType(OkHttps.JSON);

        log.info("http url: {}", UrlUtils.urlDecode(httpTask.getUrl()));
        log.info("http request body: {}", reqData);

        HttpResult httpResult = httpTask.post();

        messageContext.setResponseCode(httpResult.getStatus());
        HttpResult.Body body = httpResult.getBody().cache();
        messageContext.setResponse(body.toString());

        if (httpResult.isSuccessful()) {
            Integer errcode = HttpUtils.getValue("errcode", body, Integer.class);
            messageContext.setState(0 == errcode);
        }

        return messageContext;
    }

    @Override
    public List<MessageContext> nodeMultipleSend(List<MessageContext> messageContextList) {
        return null;
    }
}