package cn.yang37.chain.node.ding;

import cn.yang37.chain.node.adapter.MessageNodeAdapterDing;
import cn.yang37.constant.DingConstant;
import cn.yang37.entity.context.MessageContext;
import cn.yang37.entity.context.ThreadContext;
import cn.yang37.util.SignUtils;
import cn.zhxu.okhttps.HttpResult;
import cn.zhxu.okhttps.OkHttps;
import com.alibaba.fastjson2.JSON;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;


/**
 * @description:
 * @class: DingSendNode
 * @author: yang37z@qq.com
 * @date: 2023/1/28 10:55
 * @version: 1.0
 */
public class DingTextSendNode extends MessageNodeAdapterDing {

    private static final Logger log = LoggerFactory.getLogger(DingTextSendNode.class);

    @Override
    public MessageContext nodeSingleSend(MessageContext messageContext) throws Exception {
        String timestamp = messageContext.getTimestamp();

        String baseUrl = configProperties.getBaseUrl();
        String accessToken = configProperties.getAccessKey();
        String secret = configProperties.getSecret();
        log.debug("[config] baseUrl: {} ,ak: {},sk: {}", baseUrl, accessToken, secret);

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
        final String formatUrl = "{{url}}/robot/send?access_token={{accessToken}}&timestamp={{timestamp}}&sign={{sign}}";
        return formatUrl
                .replace("{{url}}", url)
                .replace("{{accessToken}}", accessToken)
                .replace("{{timestamp}}", timestamp)
                .replace("{{sign}}",
                        SignUtils.calculateSignDing(secret, timestamp));
    }


}