package cn.yang37.entity;

import cn.yang37.enums.MessageContentType;
import cn.yang37.enums.MessageSceneType;

/**
 * @description: 信息实体 父类对象
 * @class: Message
 * @author: yang37z@qq.com
 * @date: 2023/1/11 10:56
 * @version: 1.0
 */
public abstract class Message {

    /**
     * 消息类型
     */
    protected MessageContentType messageContentType;

    /**
     * 获取消息对应的场景信息
     * @return 消息场景
     */
    public abstract MessageSceneType getMessageSceneType();

    protected Message() {
    }

    protected Message(MessageContentType messageContentType) {
        this.messageContentType = messageContentType;
    }

    public MessageContentType getMessageContentType() {
        return messageContentType;
    }

    public void setMessageContentType(MessageContentType messageContentType) {
        this.messageContentType = messageContentType;
    }
}