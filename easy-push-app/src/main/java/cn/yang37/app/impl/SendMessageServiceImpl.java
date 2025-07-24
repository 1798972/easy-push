package cn.yang37.app.impl;


import cn.yang37.app.SendMessageService;
import cn.yang37.entity.context.MessageContext;
import cn.yang37.entity.message.Message;
import cn.yang37.enums.ErrorCodeEnum;
import cn.yang37.exception.EasyPushException;
import cn.yang37.exception.EpSceneParseException;
import cn.yang37.exception.npe.EpMessageNullException;
import cn.yang37.exception.npe.EpServiceNullException;
import cn.yang37.factory.MessageServiceFactory;
import cn.yang37.service.AbstractMessageService;
import cn.yang37.util.MessageProvider;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

/**
 * @description:
 * @class: DealServiceImpl
 * @author: yang37z@qq.com
 * @date: 2023/1/12 17:50
 * @version: 1.0
 */
@Slf4j
public class SendMessageServiceImpl extends MessageProvider implements SendMessageService {

    /**
     * 依赖服务工厂对象
     */
    private final MessageServiceFactory messageServiceFactory = MessageServiceFactory.instance();

    @Override
    public MessageContext singleSend(Message message) {
        MessageContext messageContext = MessageContext.builder().build();
        try {
            // message校验
            messageCheck(message);
            // 根据Message对象类型format出MessageContext
            messageContext = message2MessageContext(message);

            // 解析messageService
            AbstractMessageService messageService = messageServiceFactory.getMessageService(messageContext.getMessageSceneType());
            // service校验
            serviceCheck(messageService);

            // 执行发送
            return messageService.singleSend(messageContext);
        } catch (Exception e) {
            logException(messageContext, e);
        }
        return messageContext;
    }


    @Override
    public List<MessageContext> multipleSend(List<Message> messageList) {
        return null;
    }

    /**
     * message校验
     */
    private static void messageCheck(Message message) {
        Optional.ofNullable(message).orElseThrow(() -> ErrorCodeEnum.MESSAGE_NULL.toException(EpMessageNullException.class));
        Optional.ofNullable(message.getMessageSceneType()).orElseThrow(() -> ErrorCodeEnum.SCENE_NULL.toException(EpSceneParseException.class));
    }

    /**
     * service校验
     */
    private static void serviceCheck(AbstractMessageService messageService) {
        Optional.ofNullable(messageService).orElseThrow(() -> ErrorCodeEnum.SERVICE_NULL.toException(EpServiceNullException.class));
    }

    /**
     * 统一输出异常信息
     */
    private static void logException(MessageContext messageContext, Exception e) {
        EasyPushException wrapped = (e instanceof EasyPushException)
                ? (EasyPushException) e
                : new EasyPushException(ErrorCodeEnum.UNKNOWN, e);
        messageContext.setE(wrapped);
        log.error("easy-push运行异常, code: {}, msg: {}", wrapped.getCode(), wrapped.getMsg(), e);
    }

}