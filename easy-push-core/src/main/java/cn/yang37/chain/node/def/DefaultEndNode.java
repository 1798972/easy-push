package cn.yang37.chain.node.def;

import cn.yang37.chain.node.adapter.MessageNodeAdapter;
import cn.yang37.entity.context.MessageContext;
import cn.yang37.util.GsonUtils;
import cn.yang37.util.TraceUtils;
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
public class DefaultEndNode extends MessageNodeAdapter {
    private static final Logger log = LoggerFactory.getLogger(DefaultEndNode.class);

    @Override
    public MessageContext nodeSingleSend(MessageContext messageContext) {
        if (true) {
            Boolean state = messageContext.getState();
            log.info("result -> {}", state);
            log.info("<==== [singleSend] chain end: {}", GsonUtils.toJson(messageContext));
            TraceUtils.traceEnd();
        }
        return messageContext;
    }

    @Override
    public List<MessageContext> nodeMultipleSend(List<MessageContext> messageContextList) {
        return null;
    }
}