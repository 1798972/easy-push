package cn.yang37.service;


import cn.yang37.chain.MessageChain;
import cn.yang37.entity.context.MessageContext;

import java.util.List;

/**
 * @description: 消息策略服务
 * @class: AbstractMessageService
 * @author: yang37z@qq.com
 * @date: 2023/1/12 15:11
 * @version: 1.0
 */
public abstract class AbstractMessageService {

    /**
     * 绑定对应的消息职责链
     */
    protected MessageChain messageChain;

    /**
     * 根据信息上下文对象发送信息
     *
     * @param messageContext .
     * @return .
     */
    public MessageContext singleSend(MessageContext messageContext) {
        return messageChain.execute(messageContext);
    }

    /**
     * 根据信息上下文对象发送信息-批量
     *
     * @param messageContextList .34
     * @return .
     */
    public List<MessageContext> strategyMultipleSend(List<MessageContext> messageContextList) {
        return null;
    }

}

