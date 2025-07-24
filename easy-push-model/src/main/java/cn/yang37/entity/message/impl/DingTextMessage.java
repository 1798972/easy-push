package cn.yang37.entity.message.impl;

import cn.yang37.entity.message.Message;
import cn.yang37.enums.MessageSceneType;
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