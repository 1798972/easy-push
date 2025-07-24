package cn.yang37.easypush.test;

import cn.yang37.easypush.app.impl.SendMessageServiceImpl;
import cn.yang37.easypush.entity.context.MessageContext;
import cn.yang37.easypush.entity.message.impl.DingTextMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class DingTextTest {

    @Test
    void name1() {
        // 构建消息实体类
        DingTextMessage message = new DingTextMessage("123456789");
        // 执行发送
        MessageContext messageContext = new SendMessageServiceImpl().singleSend(message);
        // 查看发送结果
        log.info("发送结果: {}", messageContext.getState());
    }

    @Test
    void name2() {
        // 构建消息实体类
        DingTextMessage message = new DingTextMessage("123456789\n7654321");
        // 执行发送
        MessageContext messageContext = new SendMessageServiceImpl().singleSend(message);
        // 查看发送结果
        log.info("发送结果: {}", messageContext.getState());
    }
}