package cn.yang37.easypush.test;

import cn.yang37.app.impl.SendMessageServiceImpl;
import cn.yang37.entity.context.MessageContext;
import cn.yang37.entity.message.Message;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class RunExceptionTest {

    @Test
    void name() {
        Message message = null;
        // 执行发送
        MessageContext messageContext = new SendMessageServiceImpl().singleSend(message);
        // 查看发送结果
        log.info("发送结果: {}", JSON.toJSONString(messageContext));
    }

}