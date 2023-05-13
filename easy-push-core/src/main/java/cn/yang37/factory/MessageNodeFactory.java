package cn.yang37.factory;

import cn.yang37.chain.node.MessageNodeAdapter;
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
public class MessageNodeFactory {
    private static final Logger log = LoggerFactory.getLogger(MessageNodeFactory.class);

    private static final Map<Class<? extends MessageNodeAdapter>, MessageNodeAdapter> NODE_MAP = new HashMap<>();

    private static final MessageNodeFactory NODE_FACTORY = new MessageNodeFactory();

    private MessageNodeFactory() {

    }

    public static MessageNodeFactory instance() {
        return NODE_FACTORY;
    }

    public MessageNodeAdapter obtainMessageNode(Class<? extends MessageNodeAdapter> implClass) throws IllegalAccessException, InstantiationException {
        MessageNodeAdapter messageNode = NODE_MAP.get(implClass);

        if (ObjectUtils.isEmpty(messageNode)) {
            MessageNodeAdapter instance = implClass.newInstance();
            NODE_MAP.put(implClass, instance);
            return instance;
        }
        return messageNode;
    }

}