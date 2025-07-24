package cn.yang37.easypush.core.chain.node.ding;

import cn.yang37.easypush.core.chain.node.adapter.MessageNodeAdapterDing;
import cn.yang37.easypush.core.util.SignUtils;
import cn.yang37.easypush.entity.constant.DingConstant;
import cn.yang37.easypush.entity.context.MessageContext;
import cn.yang37.easypush.entity.context.ThreadContext;
import cn.zhxu.okhttps.HttpResult;
import cn.zhxu.okhttps.OkHttps;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;


/**
 * @description:
 * @class: DingSendNode
 * @author: yang37z@qq.com
 * @date: 2023/1/28 10:55
 * @version: 1.0
 */
@Slf4j
public class DingTextSendNode extends MessageNodeAdapterDing {

    @Override
    public MessageContext nodeSingleSend(MessageContext messageContext) throws Exception {
        String timestamp = messageContext.getTimestamp();

        String baseUrl = sceneConfig().getBaseUrl();
        String accessToken = sceneConfig().getAccessToken();
        String secret = sceneConfig().getSecret();
        // 配置信息
        logConfig();

        String dingUrl = formatDingSendUrl(baseUrl, accessToken, secret, timestamp);
        log.info("http url: {}", URLDecoder.decode(dingUrl, StandardCharsets.UTF_8.name()));

        Object bodyPara = ThreadContext.getContext(DingConstant.DING_REQUEST_BODY);
        log.info("http request body: {}", JSON.toJSONString(bodyPara));

        HttpResult httpResult = OkHttps.sync(dingUrl)
                .setBodyPara(bodyPara)
                .bodyType(OkHttps.JSON)
                .post();

        if (ObjectUtils.isEmpty(httpResult)) {
            log.error("failed to send HTTP request! url: {}", dingUrl);
        }

        int status = httpResult.getStatus();
        HttpResult.Body body = httpResult.getBody().cache();

        log.info("http response body[{}]: {}", status, body.toString());
        // 判断结果
        messageContext.setState(0 == body.toMapper().getInt(DingConstant.ERR_CODE));

        return messageContext;
    }

    @Override
    public List<MessageContext> nodeMultipleSend(List<MessageContext> messageContextList) {
        return null;
    }

    /**
     * 格式化钉钉url
     *
     * @param url         .
     * @param accessToken .
     * @return .
     */
    private static String formatDingSendUrl(String url, String accessToken, String secret, String timestamp) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        // 默认值
        url = Optional.ofNullable(url).orElse(DingConstant.BASE_URL);
        // 格式化
        final String formatUrl = "{{url}}/robot/send?access_token={{accessToken}}&timestamp={{timestamp}}&sign={{sign}}";
        return formatUrl
                .replace("{{url}}", url)
                .replace("{{accessToken}}", accessToken)
                .replace("{{timestamp}}", timestamp)
                .replace("{{sign}}",
                        SignUtils.calculateSignDing(secret, timestamp));
    }
}