package cn.yang37.chain.node.def;

import cn.yang37.chain.node.adapter.MessageNodeAdapter;
import cn.yang37.entity.context.MessageContext;
import com.alibaba.fastjson2.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @description:
 * @class: DefInitMessageHandler
 * @author: yang37z@qq.com
 * @date: 2023/1/13 23:09
 * @version: 1.0
 */
public class DefaultInitNode extends MessageNodeAdapter {

    private static final Logger log = LoggerFactory.getLogger(DefaultInitNode.class);

    @Override
    public MessageContext nodeSingleSend(MessageContext messageContext) {
        if (true) {
            log.info("====> [singleSend] chain begin: {}", JSON.toJSONString(messageContext));
        }
        return messageContext;
    }

    @Override
    public List<MessageContext> nodeMultipleSend(List<MessageContext> messageContextList) {
        log.debug("multipleSend,messageListSize[{}],chain begin: {}", messageContextList.size(), JSON.toJSONString(messageContextList));
        return messageContextList;
    }
}