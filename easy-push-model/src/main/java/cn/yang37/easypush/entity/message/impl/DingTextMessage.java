package cn.yang37.easypush.entity.message.impl;

import cn.yang37.easypush.entity.enums.MessageSceneType;
import cn.yang37.easypush.entity.message.Message;
import lombok.*;

/**
 * @description:
 * @class: DingTextMessage
 * @author: yang37z@qq.com
 * @date: 2023/4/1 2:06
 * @version: 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DingTextMessage extends Message {

    private String text;

    @Override
    public MessageSceneType getMessageSceneType() {
        return MessageSceneType.DING_TEXT;
    }

}