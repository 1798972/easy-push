package cn.yang37.easypush.core.chain.node.common;

import cn.yang37.easypush.core.chain.node.adapter.MessageNodeAdapter;
import cn.yang37.easypush.core.util.TraceUtils;
import cn.yang37.easypush.entity.context.MessageContext;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @description:
 * @class: DefInitMessageHandler
 * @author: yang37z@qq.com
 * @date: 2023/1/13 23:09
 * @version: 1.0
 */
@Slf4j
public class CommonEndNode extends MessageNodeAdapter {

    @Override
    public MessageContext nodeSingleSend(MessageContext messageContext) {
        if (true) {
            Boolean state = messageContext.getState();
            log.info("(MessageContext.state) result-> {}", state);
            log.info("<==== [singleSend] chain end: {}", JSON.toJSONString(messageContext));
            TraceUtils.traceEnd();
        }
        return messageContext;
    }

    @Override
    public List<MessageContext> nodeMultipleSend(List<MessageContext> messageContextList) {
        return null;
    }
}