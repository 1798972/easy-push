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
     * 根据信息上下文对象发送信息
     *
     * @param messageContext .
     * @return .
     */
    MessageContext nodeSingleSend(MessageContext messageContext) throws Exception;

    /**
     * 根据信息上下文对象发送信息-批量
     *
     * @param messageContextList .34
     * @return .
     */
    List<MessageContext> nodeMultipleSend(List<MessageContext> messageContextList);

}
