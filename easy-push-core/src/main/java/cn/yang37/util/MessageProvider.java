package cn.yang37.util;


import cn.yang37.entity.context.MessageContext;
import cn.yang37.entity.message.Message;
import cn.yang37.enums.MessageSceneType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * @description:
 * @class: SendMessage
 * @author: yang37z@qq.com
 * @date: 2023/1/11 10:39
 * @version: 1.0
 */
public abstract class MessageProvider {

    private static final Logger log = LoggerFactory.getLogger(MessageProvider.class);

    /**
     * 消息内容的类型是否合法
     *
     * @param message .
     * @return .
     */
    protected Boolean hasValidMessageContentType(Message message) {
        return true;
    }

    /**
     * 消息目的地的类型是否合法
     *
     * @param messageSceneType .
     * @return .
     */
    protected Boolean hasValidMessageDestinationType(MessageSceneType messageSceneType) {
        return true;
    }

    protected Boolean singleMessageCheck(MessageSceneType messageSceneType, Message message) {
        boolean hasValidMessageContentType = hasValidMessageContentType(message);
        boolean hasValidMessageDestinationType = hasValidMessageDestinationType(messageSceneType);

        if (!hasValidMessageContentType) {
            log.error("不合法的消息内容类型!");
        }
        if (!hasValidMessageDestinationType) {
            log.error("不合法的消息目的地类型!");
        }
        return hasValidMessageContentType && hasValidMessageDestinationType;
    }

    /**
     * 转换消息实体对象到MessageContext
     *
     * @param message .
     * @return MessageContext
     */
    protected MessageContext message2MessageContext(Message message) {
        final String split = "-";
        return MessageContext.builder()
                .id(UUID.randomUUID().toString().replace(split, ""))
                .messageSceneType(message.getMessageSceneType())
                .timestamp(String.valueOf(System.currentTimeMillis()))
                .message(message)
                .state(false)
                .build();
    }

    /**
     * 批量转换消息实体对象到MessageContext
     *
     * @param messageList .
     * @return .
     */
    protected List<MessageContext> messageList2MessageContextList(List<Message> messageList) {
        List<MessageContext> messageContextList = new LinkedList<>();
        messageList.forEach(message -> messageContextList.add(message2MessageContext(message)));
        return messageContextList;
    }
}
