package cn.yang37.chain.node.sms.ali.v3;

import cn.yang37.chain.node.adapter.MessageNodeAdapterSmsAli;
import cn.yang37.constant.SmsAliV3Constant;
import cn.yang37.entity.context.MessageContext;
import cn.yang37.entity.context.ThreadContext;
import cn.yang37.util.DateUtils;
import cn.yang37.util.StringUtils;

import java.util.List;

/**
 * @description:
 * @class: AliSmsParamNode
 * @author: yang37z@qq.com
 * @date: 2024/5/21 16:03
 * @version: 1.0
 */
public class SmsAliV3ParamNode extends MessageNodeAdapterSmsAli {

    @Override
    public MessageContext nodeSingleSend(MessageContext messageContext) throws Exception {

        String url = configProperties.getBaseUrl();
        String date = DateUtils.getDateTimeIso8601();
        String host = StringUtils.parseHostFromUrl(url);
        String id = messageContext.getId();

        // 填充参数
        ThreadContext.putContext(SmsAliV3Constant.X_ACS_ACTION, "SendSms");
        ThreadContext.putContext(SmsAliV3Constant.X_ACS_VERSION, "2017-05-25");
        ThreadContext.putContext(SmsAliV3Constant.X_ACS_DATE, date);
        ThreadContext.putContext(SmsAliV3Constant.X_ACS_SIGNATURE_NONCE, id);
        ThreadContext.putContext(SmsAliV3Constant.HOST, host);

        return messageContext;
    }

    @Override
    public List<MessageContext> nodeMultipleSend(List<MessageContext> messageContextList) {
        return null;
    }
}
