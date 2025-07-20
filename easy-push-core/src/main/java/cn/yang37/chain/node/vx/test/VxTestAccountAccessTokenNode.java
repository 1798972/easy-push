package cn.yang37.chain.node.vx.test;

import cn.yang37.chain.node.adapter.MessageNodeAdapterVxTestAccountMessage;
import cn.yang37.constant.VxTestAccountConstant;
import cn.yang37.entity.context.MessageContext;
import cn.yang37.entity.context.ThreadContext;
import cn.yang37.util.HttpUtils;
import cn.yang37.util.JsonUtils;
import cn.zhxu.okhttps.HttpResult;
import cn.zhxu.okhttps.OkHttps;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @description:
 * @class: GetAccessTokenNode
 * @author: yang37z@qq.com
 * @date: 2024/7/12 16:54
 * @version: 1.0
 */
@Slf4j
public class VxTestAccountAccessTokenNode extends MessageNodeAdapterVxTestAccountMessage {

    @Override
    public MessageContext nodeSingleSend(MessageContext messageContext) throws Exception {
        String appId = sceneConfig().getAppId();
        String appSecret = sceneConfig().getAppSecret();

        // 构建请求
        String tokenUrl = HttpUtils.formatSendUrl(VxTestAccountConstant.URL, sceneConfig().getBaseUrl(), VxTestAccountConstant.GetAccessToken.PATH);
        HttpResult httpResult = OkHttps
                .sync(tokenUrl)
                .addUrlPara(VxTestAccountConstant.GetAccessToken.GRANT_TYPE, VxTestAccountConstant.GetAccessToken.CLIENT_CREDENTIAL)
                .addUrlPara(VxTestAccountConstant.GetAccessToken.APP_ID, appId)
                .addUrlPara(VxTestAccountConstant.GetAccessToken.SECRET, appSecret)
                .get();

        if (httpResult.isSuccessful()) {
            HttpResult.Body body = httpResult.getBody().cache();
            String accessToken = JsonUtils.getValue("access_token", body, String.class);
            Double expiresIn = JsonUtils.getValue("expires_in", body, Double.class);

            ThreadContext.putContext(VxTestAccountConstant.GetAccessToken.ACCESS_TOKEN, accessToken);
            ThreadContext.putContext(VxTestAccountConstant.GetAccessToken.EXPIRES_IN, expiresIn);
            log.debug("accessToken: {}", accessToken);
            log.debug("expiresIn: {}", expiresIn);
        }

        return messageContext;
    }

    @Override
    public List<MessageContext> nodeMultipleSend(List<MessageContext> messageContextList) {
        return null;
    }
}