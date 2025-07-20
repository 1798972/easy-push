package cn.yang37.entity.message.impl;

import cn.yang37.entity.message.Message;
import cn.yang37.enums.MessageSceneType;
import lombok.*;

/**
 * @description:
 * @class: AliSmsMessage
 * @author: yang37z@qq.com
 * @date: 2024/5/21 15:52
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SmsAliV3Message extends Message {

    /**
     * 接收短信的手机号码
     */
    private String PhoneNumbers;

    /**
     * 短信签名
     */
    private String SignName;

    /**
     * 短信模板 Code
     */
    private String TemplateCode;

    /**
     * 短信模板变量对应的实际值，支持传入多个参数
     */
    private String TemplateParam;

    /**
     * 上行短信扩展码
     */
    private String SmsUpExtendCode;

    /**
     * 外部流水扩展字段
     */
    private String OutId;

    @Override
    public MessageSceneType getMessageSceneType() {
        return MessageSceneType.SMS_ALI_V3;
    }
}