package cn.yang37.easypush.entity.context;

import cn.yang37.easypush.entity.enums.MessageSceneType;
import cn.yang37.easypush.entity.message.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 发送信息, 上下文对象
 * @class: Message
 * @author: yang37z@qq.com
 * @date: 2023/1/11 10:34
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageContext {

    /**
     * 消息id
     */
    private String id;

    /**
     * 消息类型
     */
    private MessageSceneType messageSceneType;

    /**
     * 消息对象
     */
    private Message message;

    /**
     * 时间戳
     */
    private String timestamp;

    /**
     * 消息发送状态
     */
    private Boolean state;

    /**
     * 执行异常
     */
    private Throwable e;

    /**
     * 提示信息
     */
    private String info;

    /**
     * 返回状态码
     */
    private int responseCode;

    /**
     * 返回报文
     */
    private String response;

}