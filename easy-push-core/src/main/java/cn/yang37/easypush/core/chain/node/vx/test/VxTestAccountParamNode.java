package cn.yang37.easypush.core.chain.node.vx.test;

import cn.yang37.easypush.core.chain.node.adapter.MessageNodeAdapterVxTestAccountMessage;
import cn.yang37.easypush.core.util.TraceUtils;
import cn.yang37.easypush.entity.context.MessageContext;
import cn.yang37.easypush.entity.message.impl.VxTestAccountMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @description:
 * @class: GetAccessTokenNode
 * @author: yang37z@qq.com
 * @date: 2024/7/12 16:54
 * @version: 1.0
 */
@Slf4j
public class VxTestAccountParamNode extends MessageNodeAdapterVxTestAccountMessage {

    @Override
    public MessageContext nodeSingleSend(MessageContext messageContext) throws Exception {
        VxTestAccountMessage vxTestAccountMessage = (VxTestAccountMessage) messageContext.getMessage();
        // 填充clientMsgId
        String clientMsgId = vxTestAccountMessage.getClientMsgId();
        if (StringUtils.isEmpty(clientMsgId)) {
            vxTestAccountMessage.setClientMsgId(TraceUtils.getTraceId());
        }

        return messageContext;
    }

    @Override
    public List<MessageContext> nodeMultipleSend(List<MessageContext> messageContextList) {
        return null;
    }
}