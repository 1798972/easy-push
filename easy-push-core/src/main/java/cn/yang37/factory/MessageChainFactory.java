package cn.yang37.factory;

import cn.yang37.chain.MessageChain;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class MessageChainFactory {

    /**
     * 职责链实现类包路径
     */
    private static final String PACKAGE_MESSAGE_CHAIN = "cn.yang37.chain.impl";

    /**
     * 职责链对象池
     */
    private final Map<Class<? extends MessageChain>, MessageChain> clazz4Chain = new ConcurrentHashMap<>();

    private MessageChainFactory() {
        registerChains();
    }

    /**
     * 自动扫描并注册所有 MessageChain 实现（template 包及子包）
     */
    private void registerChains() {
        Reflections reflections = new Reflections(PACKAGE_MESSAGE_CHAIN);
        Set<Class<? extends MessageChain>> chainClasses = reflections.getSubTypesOf(MessageChain.class);
        for (Class<? extends MessageChain> clazz : chainClasses) {
            try {
                MessageChain chain = clazz.getDeclaredConstructor().newInstance();
                clazz4Chain.put(clazz, chain);
                log.info("[对象池初始化][MessageChain][clazz -> MessageChain] 初始化成功,{} -> @{}", clazz, System.identityHashCode(chain));
            } catch (Exception e) {
                log.info("[对象池初始化][MessageChain] 初始化失败,class: {}", clazz, e);
            }
        }
    }

    private static class Holder {
        private static final MessageChainFactory INSTANCE = new MessageChainFactory();
    }

    public static MessageChainFactory instance() {
        return Holder.INSTANCE;
    }

    /**
     * 获取已注册的 chain
     */
    public MessageChain getMessageChain(Class<? extends MessageChain> clazz) {
        return clazz4Chain.get(clazz);
    }

    /**
     * 可支持动态注册
     */
    public void registerChain(Class<? extends MessageChain> clazz, MessageChain chain) {
        clazz4Chain.put(clazz, chain);
    }
}
