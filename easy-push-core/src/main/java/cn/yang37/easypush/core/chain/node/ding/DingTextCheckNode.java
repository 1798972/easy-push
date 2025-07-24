package cn.yang37.easypush.core.chain.node.ding;


import cn.yang37.easypush.core.chain.node.adapter.MessageNodeAdapterDing;
import cn.yang37.easypush.entity.context.MessageContext;
import cn.yang37.easypush.entity.enums.MessageContentType;
import cn.yang37.easypush.entity.message.Message;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @description:
 * @class: DingInitNode
 * @author: yang37z@qq.com
 * @date: 2023/1/13 10:00
 * @version: 1.0
 */
@Slf4j
public class DingTextCheckNode extends MessageNodeAdapterDing {

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