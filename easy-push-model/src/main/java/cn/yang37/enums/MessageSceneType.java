package cn.yang37.enums;

/**
 * @description: 消息场景的类型
 * @class: MessageEnum
 * @author: yang37z@qq.com
 * @date: 2023/1/11 10:28
 * @version: 1.0
 */
public enum MessageSceneType {

    /**
     * 未知
     */
    UNKNOWN,

    /**
     * 短信_腾讯云
     */
    SMS_TENCENT_V3,

    /**
     * 短信_阿里云
     */
    SMS_ALI_V3,

    /**
     * 邮件
     */
    EMAIL,

    /**
     * 钉钉
     */
    DING,

    /**
     * 微信测试号
     */
    VX_TEST_ACCOUNT,

    ;

}
