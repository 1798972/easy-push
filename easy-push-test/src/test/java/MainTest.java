import cn.yang37.app.impl.SendMessageServiceImpl;
import cn.yang37.entity.MessageContext;
import cn.yang37.entity.sence.ding.DingTextMessage;
import cn.yang37.util.GsonUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class MainTest {

    private static final Logger log = LoggerFactory.getLogger(MainTest.class);

    @Test
    void name() {
        MessageContext messageContext = new SendMessageServiceImpl().singleSend(new DingTextMessage("abcdefghijk"));
        String json = GsonUtil.toJsonPretty(messageContext);
        log.info("结果:\n{}", json);
    }
}