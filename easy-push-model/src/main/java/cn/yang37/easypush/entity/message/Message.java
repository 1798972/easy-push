package cn.yang37.easypush.entity.message;

import cn.yang37.easypush.entity.enums.MessageContentType;
import cn.yang37.easypush.entity.enums.MessageSceneType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 信息实体 父类对象
 * @class: Message
 * @author: yang37z@qq.com
 * @date: 2023/1/11 10:56
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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

}