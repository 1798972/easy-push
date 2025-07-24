package cn.yang37.easypush.core.factory;

import cn.yang37.easypush.core.chain.node.adapter.MessageNodeAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @class: NodeFactory
 * @author: yang37z@qq.com
 * @date: 2023/4/13 0:19
 * @version: 1.0
 */
@Slf4j
public class MessageNodeFactory {

    private final Map<Class<? extends MessageNodeAdapter>, MessageNodeAdapter> nodePool = new ConcurrentHashMap<>();

    private MessageNodeFactory() {
    }

    private static class Holder {
        private static final MessageNodeFactory INSTANCE = new MessageNodeFactory();
    }

    public static MessageNodeFactory instance() {
        return Holder.INSTANCE;
    }

    public MessageNodeAdapter getMessageNode(Class<? extends MessageNodeAdapter> clazz) {
        return nodePool.computeIfAbsent(clazz, key -> {
            try {
                return key.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Failed to instantiate MessageNodeAdapter: " + clazz.getName(), e);
            }
        });
    }
}