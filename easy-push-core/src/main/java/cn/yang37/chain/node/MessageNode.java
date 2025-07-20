package cn.yang37.chain.node;

import cn.yang37.entity.context.MessageContext;

import java.util.List;

/**
 * @description:
 * @class: MessageNode
 * @author: yang37z@qq.com
 * @date: 2023/4/13 1:18
 * @version: 1.0
 */
public interface MessageNode {

    /**
     * 发送单条消息
     *
     * @param messageContext 消息上下文对象
     * @return 消息上下文对象
     * @throws Exception .
     */
    MessageContext nodeSingleSend(MessageContext messageContext) throws Exception;

    /**
     * 发送单条消息
     *
     * @param messageContextList 消息上下文对象List
     * @return 消息上下文对象
     */
    List<MessageContext> nodeMultipleSend(List<MessageContext> messageContextList);

}
