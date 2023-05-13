package cn.yang37.factory;

import cn.yang37.chain.example.MessageChain;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @class: NodeFactory
 * @author: yang37z@qq.com
 * @date: 2023/4/13 0:19
 * @version: 1.0
 */
public class MessageChainFactory {
    private static final Logger log = LoggerFactory.getLogger(MessageChainFactory.class);

    private static final Map<Class<? extends MessageChain>, MessageChain> CHAIN_MAP = new HashMap<>();

    private static final MessageChainFactory NODE_FACTORY = new MessageChainFactory();

    private MessageChainFactory() {

    }

    public static MessageChainFactory instance() {
        return NODE_FACTORY;
    }

    public MessageChain obtainMessageChain(Class<? extends MessageChain> implClass) throws IllegalAccessException, InstantiationException {
        MessageChain chain = CHAIN_MAP.get(implClass);
        if (ObjectUtils.isEmpty(chain)) {
            MessageChain instance = implClass.newInstance();
            CHAIN_MAP.put(implClass, instance);
            return instance;
        }
        return chain;
    }

}