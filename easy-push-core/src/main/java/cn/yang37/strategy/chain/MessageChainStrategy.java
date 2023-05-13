package cn.yang37.strategy.chain;

import cn.yang37.chain.example.MessageChain;
import cn.yang37.chain.example.MessageChainDing;
import cn.yang37.factory.MessageChainFactory;
import cn.yang37.service.execute.AbstractMessageService;
import cn.yang37.service.execute.impl.DingTextMessageServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 消息职责链策略, 聚合消息职责链.
 * @class: AbstractMessageStrategyFactory
 * @author: yang37z@qq.com
 * @date: 2023/1/12 18:42
 * @version: 1.0
 */
public class MessageChainStrategy {

    private static final Logger log = LoggerFactory.getLogger(MessageChainStrategy.class);

    private final static MessageChainFactory MESSAGE_CHAIN_FACTORY = MessageChainFactory.instance();

    protected final static Map<Class<? extends AbstractMessageService>, Class<? extends MessageChain>> CHAIN_CLASS_MAP = new HashMap<>();

    private final static Map<Class<? extends AbstractMessageService>, MessageChain> CHAIN_MAP = new HashMap<>();

    /**
     * 消息服务绑定职责链
     */
    public void init() {
        CHAIN_CLASS_MAP.put(DingTextMessageServiceImpl.class, MessageChainDing.class);
    }

    public MessageChain getMessageChain(Class<? extends AbstractMessageService> messageService) {
        return CHAIN_MAP.get(messageService);
    }

    public void classInitBean() {
        CHAIN_CLASS_MAP.forEach((key, className) -> {
            try {
                CHAIN_MAP.put(key, MESSAGE_CHAIN_FACTORY.obtainMessageChain(className));
            } catch (Exception e) {
                log.error("策略中填充[消息职责链对象]时,填充异常!", e);
            }
        });
    }

    public void showInitInfo() {
        log.debug("---------------------------------------------------------------");
        log.debug("[MessageChain][策略] - strategyMap,Size: {}", CHAIN_MAP.size());

        CHAIN_MAP.forEach((k, v) -> {
            log.debug("[MessageChain][策略] - strategyMap,Info:  {} -> {}", k, v);
        });
        log.debug("---------------------------------------------------------------");
    }
}