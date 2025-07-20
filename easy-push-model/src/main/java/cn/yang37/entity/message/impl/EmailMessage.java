package cn.yang37.entity.message.impl;

import cn.yang37.entity.message.Message;
import cn.yang37.enums.MessageSceneType;
import lombok.*;

/**
 * @description:
 * @class: Email
 * @author: yang37z@qq.com
 * @date: 2024/5/22 23:37
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class EmailMessage extends Message {

    private String address;

    @Override
    public MessageSceneType getMessageSceneType() {
        return MessageSceneType.EMAIL;
    }
}