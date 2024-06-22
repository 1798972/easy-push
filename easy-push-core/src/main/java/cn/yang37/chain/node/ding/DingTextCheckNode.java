package cn.yang37.chain.node.ding;


import cn.yang37.chain.node.adapter.MessageNodeAdapterDing;
import cn.yang37.entity.context.MessageContext;
import cn.yang37.entity.message.Message;
import cn.yang37.enums.MessageContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @description:
 * @class: DingInitNode
 * @author: yang37z@qq.com
 * @date: 2023/1/13 10:00
 * @version: 1.0
 */
public class DingTextCheckNode extends MessageNodeAdapterDing {
    private static final Logger log = LoggerFactory.getLogger(DingTextCheckNode.class);

    @Override
    public MessageContext nodeSingleSend(MessageContext messageContext) {
        // 检查配置信息
        // Boolean messageCheck = singleMessageCheck(messageContext.getMessageSceneType(), messageContext.getMessage());

        return messageContext;
    }

    @Override
    public List<MessageContext> nodeMultipleSend(List<MessageContext> messageContextList) {
        return null;
    }

    @Override
    protected Boolean hasValidMessageContentType(Message message) {
        // 暂时只支持Text类型
        return MessageContentType.TEXT == message.getMessageContentType();
    }

}