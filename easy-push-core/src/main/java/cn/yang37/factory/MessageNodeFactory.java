package cn.yang37.factory;

import cn.yang37.chain.node.adapter.MessageNodeAdapter;
import org.apache.commons.lang3.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @class: NodeFactory
 * @author: yang37z@qq.com
 * @date: 2023/4/13 0:19
 * @version: 1.0
 */
public class MessageNodeFactory {

    private volatile static MessageNodeFactory messageNodeFactory;

    private final Map<Class<? extends MessageNodeAdapter>, MessageNodeAdapter> nodePool = new HashMap<>();

    private MessageNodeFactory() {
    }

    public static MessageNodeFactory instance() {
        if (messageNodeFactory == null) {
            synchronized (MessageNodeFactory.class) {
                if (messageNodeFactory == null) {
                    messageNodeFactory = new MessageNodeFactory();
                }
            }
        }
        return messageNodeFactory;
    }

    public MessageNodeAdapter getMessageNode(Class<? extends MessageNodeAdapter> implClass) throws IllegalAccessException, InstantiationException {
        // 默认从缓存池获取
        MessageNodeAdapter messageNode = nodePool.get(implClass);

        // 没有则新建,并放入缓存池
        if (ObjectUtils.isEmpty(messageNode)) {
            MessageNodeAdapter newMessageNode = implClass.newInstance();
            nodePool.put(implClass, newMessageNode);
            return newMessageNode;
        }

        return messageNode;
    }

}