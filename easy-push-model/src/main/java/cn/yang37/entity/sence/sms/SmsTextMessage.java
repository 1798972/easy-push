package cn.yang37.entity.sence.sms;

import cn.yang37.entity.Message;
import cn.yang37.enums.MessageSceneType;

/**
 * @description:
 * @class: SmsTextMessage
 * @author: yang37z@qq.com
 * @date: 2023/5/13 18:33
 * @version: 1.0
 */
public class SmsTextMessage extends Message {

    @Override
    public MessageSceneType getMessageSceneType() {
        return MessageSceneType.SMS;
    }
}