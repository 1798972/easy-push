package cn.yang37.entity.message.impl;

import cn.yang37.entity.message.Message;
import cn.yang37.enums.MessageSceneType;
import lombok.*;

/**
 * @description: 腾讯云短信
 * @class: TencentSmsTextMessage
 * @author: yang37z@qq.com
 * @date: 2023/9/5 22:27
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SmsTencentV3Message extends Message {

    @Override
    public MessageSceneType getMessageSceneType() {
        return MessageSceneType.SMS_TENCENT_V3;
    }

    private String[] phoneNumberSet;
    private String smsSdkAppId;
    private String templateId;
    private String signName;
    private String[] templateParamSet;
    private String extendCode;
    private String sessionContext;
    private String senderId;

}