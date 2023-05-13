package cn.yang37.entity.sence.ding;

import cn.yang37.entity.Message;
import cn.yang37.enums.MessageContentType;
import cn.yang37.enums.MessageSceneType;

/**
 * @description:
 * @class: DingTextMessage
 * @author: yang37z@qq.com
 * @date: 2023/4/1 2:06
 * @version: 1.0
 */
public class DingTextMessage extends Message {

    private String text;

    public DingTextMessage(String text) {
        this.messageContentType = MessageContentType.TEXT;
        this.text = text;
    }

    @Override
    public MessageSceneType getMessageSceneType() {
        return MessageSceneType.DING;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}