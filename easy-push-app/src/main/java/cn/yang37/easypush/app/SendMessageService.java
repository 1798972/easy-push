package cn.yang37.easypush.app;


import cn.yang37.easypush.entity.context.MessageContext;
import cn.yang37.easypush.entity.message.Message;

import java.util.List;

/**
 * @description:
 * @class: DealService
 * @author: yang37z@qq.com
 * @date: 2023/1/12 17:37
 * @version: 1.0
 */
public interface SendMessageService {

    /**
     * 发送单条消息
     *
     * @param message .
     * @return .
     */
    MessageContext singleSend(Message message);

    /**
     * 发送多条消息 .
     *
     * @param messageList .
     * @return .
     */
    List<MessageContext> multipleSend(List<Message> messageList);

}
