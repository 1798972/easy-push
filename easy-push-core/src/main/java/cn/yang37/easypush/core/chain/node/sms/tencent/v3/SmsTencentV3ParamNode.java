package cn.yang37.easypush.core.chain.node.sms.tencent.v3;

import cn.yang37.easypush.core.chain.node.adapter.MessageNodeAdapterSmsTencentV3;
import cn.yang37.easypush.core.util.DateUtils;
import cn.yang37.easypush.core.util.StringUtils;
import cn.yang37.easypush.entity.constant.SmsTencentV3Constant;
import cn.yang37.easypush.entity.context.MessageContext;
import cn.yang37.easypush.entity.context.ThreadContext;

import java.util.List;

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
        String date = DateUtils.formatMillisToUtcDate(Long.parseLong(timestamp));

        // 加载参数
        String url = sceneConfig().getBaseUrl();
        String secretId = sceneConfig().getSecretId();
        String secretKey = sceneConfig().getSecretKey();
        String region = sceneConfig().getRegion();
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