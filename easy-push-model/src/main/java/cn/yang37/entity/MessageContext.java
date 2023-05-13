package cn.yang37.entity;

import cn.yang37.enums.MessageSceneType;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 发送信息, 上下文对象
 * @class: Message
 * @author: yang37z@qq.com
 * @date: 2023/1/11 10:34
 * @version: 1.0
 */
public class MessageContext {

    private String id;

    private MessageSceneType messageSceneType;

    private Message message;

    private String timestamp;

    private Boolean state;

    /**
     * 扩展Map存放临时信息
     */
    private Map<String, Object> extMap = new HashMap<>();

    public void extMapPut(String k, Object v) {
        extMap.put(k, v);
    }

    public Object extMapGet(String k) {
        return extMap.get(k);
    }

    private MessageContext(Builder builder) {
        setId(builder.id);
        setMessageSceneType(builder.messageSceneType);
        setMessage(builder.message);
        setTimestamp(builder.timestamp);
        setState(builder.state);
        setExtMap(builder.extMap);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MessageSceneType getMessageSceneType() {
        return messageSceneType;
    }

    public void setMessageSceneType(MessageSceneType messageSceneType) {
        this.messageSceneType = messageSceneType;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Map<String, Object> getExtMap() {
        return extMap;
    }

    private void setExtMap(Map<String, Object> extMap) {
        this.extMap = extMap;
    }


    public static final class Builder {
        private String id;
        private MessageSceneType messageSceneType;
        private Message message;
        private String timestamp;
        private Boolean state;
        private Map<String, Object> extMap = new HashMap<>();

        public Builder() {
        }

        public Builder id(String val) {
            id = val;
            return this;
        }

        public Builder messageSceneType(MessageSceneType val) {
            messageSceneType = val;
            return this;
        }

        public Builder message(Message val) {
            message = val;
            return this;
        }

        public Builder timestamp(String val) {
            timestamp = val;
            return this;
        }

        public Builder state(Boolean val) {
            state = val;
            return this;
        }

        public Builder extMap(Map<String, Object> val) {
            extMap = val;
            return this;
        }

        public MessageContext build() {
            return new MessageContext(this);
        }
    }
}