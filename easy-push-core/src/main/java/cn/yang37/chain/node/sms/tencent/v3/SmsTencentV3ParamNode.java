package cn.yang37.chain.node.sms.tencent.v3;

import cn.yang37.chain.node.adapter.MessageNodeAdapterSmsTencentV3;
import cn.yang37.constant.AppConstant;
import cn.yang37.constant.SmsTencentV3Constant;
import cn.yang37.entity.context.MessageContext;
import cn.yang37.entity.context.ThreadContext;
import cn.yang37.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * @description:
 * @class: TencentSmsV3ParamNode
 * @author: yang37z@qq.com
 * @date: 2024/3/2 21:24
 * @version: 1.0
 */
public class SmsTencentV3ParamNode extends MessageNodeAdapterSmsTencentV3 {

    @Override
    public MessageContext nodeSingleSend(MessageContext messageContext) throws Exception {

        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        AppConstant.YYYY_MM_DD.setTimeZone(TimeZone.getTimeZone("UTC"));
        String date = AppConstant.YYYY_MM_DD.format(new Date(Long.parseLong(timestamp + "000")));

        // 加载参数
        String url = configProperties.getBaseUrl();
        String secretId = configProperties.getSecretId();
        String secretKey = configProperties.getSecretKey();
        String region = configProperties.getRegion();
        String host = StringUtils.parseHostFromUrl(url);

        // 填充参数
        ThreadContext.putContext(SmsTencentV3Constant.TIMESTAMP, timestamp);
        ThreadContext.putContext(SmsTencentV3Constant.DATE, date);
        ThreadContext.putContext(SmsTencentV3Constant.URL, url);
        ThreadContext.putContext(SmsTencentV3Constant.HOST, host);
        ThreadContext.putContext(SmsTencentV3Constant.SECRET_ID, secretId);
        ThreadContext.putContext(SmsTencentV3Constant.SECRET_KEY, secretKey);
        ThreadContext.putContext(SmsTencentV3Constant.REGION, region);

        return messageContext;
    }

    @Override
    public List<MessageContext> nodeMultipleSend(List<MessageContext> messageContextList) {
        return null;
    }
}