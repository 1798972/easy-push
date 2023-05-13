package cn.yang37.factory;


import cn.yang37.strategy.chain.MessageChainStrategy;
import org.apache.commons.lang3.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 获取职责链
 * @class: ExecuteMessageServiceFactory
 * @author: yang37z@qq.com
 * @date: 2023/1/12 15:11
 * @version: 1.0
 */
public class MessageChainStrategyFactory {

    private static final MessageChainStrategyFactory MESSAGE_CHAIN_STRATEGY_FACTORY = new MessageChainStrategyFactory();

    private final Map<Class<? extends MessageChainStrategy>, MessageChainStrategy> chainStrategyMap = new HashMap<>();

    private MessageChainStrategyFactory() {
    }

    public static MessageChainStrategyFactory instance() {
        return MESSAGE_CHAIN_STRATEGY_FACTORY;
    }

    public MessageChainStrategy obtainMessageChainStrategy() throws IllegalAccessException, InstantiationException {
        MessageChainStrategy messageChainStrategy = chainStrategyMap.get(MessageChainStrategy.class);

        if (ObjectUtils.isEmpty(messageChainStrategy)){
            MessageChainStrategy chainStrategy = new MessageChainStrategy();
            afterCreate(chainStrategy);

            return chainStrategy;
        }
        return messageChainStrategy;
    }

    private void afterCreate(MessageChainStrategy chainStrategy) throws IllegalAccessException, InstantiationException {
        chainStrategy.init();
        chainStrategy.classInitBean();
        chainStrategy.showInitInfo();
        // 放入对象
        chainStrategyMap.put(MessageChainStrategy.class, chainStrategy);
    }

}

