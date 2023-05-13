package cn.yang37.chain.node.ding;

import cn.yang37.config.DingTextConfigProperties;
import cn.yang37.constant.DingConstant;
import cn.yang37.constant.LogConstant;
import cn.yang37.entity.MessageContext;
import cn.yang37.util.HexUtils;
import cn.zhxu.okhttps.HttpResult;
import cn.zhxu.okhttps.OkHttps;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.cxf.common.util.UrlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
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
public class DingSendNode extends DingMessageNodeAdapter {

    private static final Logger log = LoggerFactory.getLogger(DingSendNode.class);


    @Override
    public MessageContext nodeSingleSend(MessageContext messageContext) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, ConfigurationException {

        String timestamp = messageContext.getTimestamp();

        Configuration config = new PropertiesConfiguration("./easy-push.properties");

        String formatSendUrl = formatDingSendUrl(
                config.getString(DingTextConfigProperties.PREFIX + "base-url")
                , config.getString(DingTextConfigProperties.PREFIX + "access-key")
                , config.getString(DingTextConfigProperties.PREFIX + "secret")
                , timestamp);

        log.info("{}req url: {}", LogConstant.Ding.PRE, UrlUtils.urlDecode(formatSendUrl));

        HttpResult.Body body = OkHttps.sync(formatSendUrl)
                .setBodyPara(messageContext.extMapGet(DingConstant.DING_REQUEST_BODY))
                .bodyType(OkHttps.JSON)
                .post()
                .getBody()
                .cache();

        log.info("{}req response: {}", LogConstant.Ding.PRE, body.toString());
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
        return formatUrl.replace("{{url}}", url).replace("{{accessToken}}", accessToken).replace("{{timestamp}}", timestamp).replace("{{sign}}", calculateDingSign(secret, timestamp));
    }

    /**
     * 计算钉钉签名
     */
    private static String calculateDingSign(String secret, String timestamp) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
        return URLEncoder.encode(HexUtils.byteArr2Base64(signData));
    }

}