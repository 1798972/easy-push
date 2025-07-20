import cn.yang37.app.impl.SendMessageServiceImpl;
import cn.yang37.entity.context.MessageContext;
import cn.yang37.entity.message.impl.DingTextMessage;
import cn.yang37.entity.message.impl.SmsAliV3Message;
import cn.yang37.entity.message.impl.SmsTencentV3Message;
import cn.yang37.entity.message.impl.VxTestAccountMessage;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class MainTest {

    /**
     * 钉钉文本类消息
     */
    @Test
    void sendDingTextMessage() {
        // 构建消息实体类
        DingTextMessage message = new DingTextMessage("123456789");
        // 执行发送
        MessageContext messageContext = new SendMessageServiceImpl().singleSend(message);
        // 查看发送结果
        log.info("发送结果: {}", messageContext.getState());
    }

    /**
     * 腾讯云短信V3版
     */
    @Test
    void sendTencentSmsV3Message() {
        SmsTencentV3Message smsTencentV3Message = SmsTencentV3Message.builder()
                .phoneNumberSet(new String[]{"18712341234"})
                .smsSdkAppId("1400xxxx")
                .signName("yang37")
                .templateId("xxxx")
                .templateParamSet(new String[]{"123456"})
                .sessionContext("SessionContext")
                .build();

        MessageContext messageContext = new SendMessageServiceImpl().singleSend(smsTencentV3Message);
        log.info("发送结果: {}", messageContext.getState());
    }

    /**
     * 阿里云短信V3版
     */
    @Test
    void sendSmsAliV3Message() {
        SmsAliV3Message smsAliV3Message = SmsAliV3Message.builder()
                .PhoneNumbers("18712341234")
                .SignName("阿里云短信测试")
                .TemplateCode("SMS_154950909")
                .TemplateParam("{\"code\":\"187456\"}")
                .build();

        MessageContext messageContext = new SendMessageServiceImpl().singleSend(smsAliV3Message);
        log.info("messageContext: {}", JSON.toJSONString(messageContext));
        log.info("发送结果: {}", messageContext.getState());
    }

    @Test
    void sendVxTestAccountMessage() {

        VxTestAccountMessage vxTestAccountMessage = VxTestAccountMessage.builder()
                .touser("xxx")
                .templateId("xxx")
                .url("")
                .data("title", "标题1")
                .data("time", "时间2")
                .data("content", "内容3")
                .build();

        MessageContext messageContext = new SendMessageServiceImpl().singleSend(vxTestAccountMessage);
        log.info("messageContext: {}", JSON.toJSONString(messageContext));
        log.info("发送结果: {}", messageContext.getState());
    }
}