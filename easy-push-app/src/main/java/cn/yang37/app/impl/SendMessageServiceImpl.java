package cn.yang37.app.impl;


import cn.yang37.app.SendMessageService;
import cn.yang37.entity.Message;
import cn.yang37.entity.MessageContext;
import cn.yang37.factory.MessageServiceStrategyFactory;
import cn.yang37.service.execute.AbstractMessageService;
import cn.yang37.strategy.service.ProxyMessageServiceStrategy;
import cn.yang37.util.MessageProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @description:
 * @class: DealServiceImpl
 * @author: yang37z@qq.com
 * @date: 2023/1/12 17:50
 * @version: 1.0
 */
public class SendMessageServiceImpl extends MessageProvider implements SendMessageService {

    private static final Logger log = LoggerFactory.getLogger(SendMessageServiceImpl.class);

    @Override
    public MessageContext singleSend(Message message) {
        MessageContext messageContext = new MessageContext.Builder().build();
        try {
            // 根据Message对象类型format出MessageContext
            messageContext = this.message2MessageContext(message);

            // ProxyMessageServiceStrategy获取实际消息策略对象后,选取消息服务
            AbstractMessageService messageService = MessageServiceStrategyFactory.instance().obtainMessageServiceStrategy(ProxyMessageServiceStrategy.class).getMessageService(messageContext.getMessageSceneType());
            log.debug("[MessageSceneType|{}]获取消息服务对象: {}", messageContext.getMessageSceneType(), messageService.getClass());

            return messageService.singleSend(messageContext);

        } catch (Exception e) {
            log.error("策略执行异常!", e);
        }
        return messageContext;
    }

    @Override
    public List<MessageContext> multipleSend(List<Message> messageList) {
        return null;
    }
}