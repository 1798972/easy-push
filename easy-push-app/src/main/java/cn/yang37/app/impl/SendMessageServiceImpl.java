package cn.yang37.app.impl;


import cn.yang37.app.SendMessageService;
import cn.yang37.entity.context.MessageContext;
import cn.yang37.entity.message.Message;
import cn.yang37.exception.ExecuteException;
import cn.yang37.factory.MessageServiceFactory;
import cn.yang37.service.AbstractMessageService;
import cn.yang37.util.GsonUtils;
import cn.yang37.util.MessageProvider;
import cn.yang37.util.TraceUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

import java.util.List;

/**
 * @description:
 * @class: DealServiceImpl
 * @author: yang37z@qq.com
 * @date: 2023/1/12 17:50
 * @version: 1.0
 */
@Slf4j
public class SendMessageServiceImpl extends MessageProvider implements SendMessageService {

    @Override
    public MessageContext singleSend(Message message) {
        MessageContext messageContext = MessageContext.builder().build();
        try {
            // 根据Message对象类型format出MessageContext
            messageContext = message2MessageContext(message);

            // 格式化日志信息
            formatTraceId(messageContext);
            log.debug("message: {},messageContext: {}", GsonUtils.toJson(message), GsonUtils.toJson(messageContext));

            // 解析MessageService对象
            AbstractMessageService messageService = MessageServiceFactory.instance().getMessageService(messageContext.getMessageSceneType());

            if (ObjectUtils.isEmpty(messageService)) {
                throw new ExecuteException("无法查找到实体类对应的消息服务!");
            }

            // 执行发送
            return messageService.singleSend(messageContext);
        } catch (Exception e) {
            messageContext.setState(false);
            log.error("easy-push消息发送异常!", e);
        }

        return messageContext;
    }

    @Override
    public List<MessageContext> multipleSend(List<Message> messageList) {
        return null;
    }

    private static void formatTraceId(MessageContext messageContext) {
        TraceUtils.traceStart(messageContext.getId(), messageContext.getMessageSceneType().name());
    }
}