package cn.yang37.factory;

import cn.yang37.chain.MessageChain;
import cn.yang37.chain.template.MessageChainDing;
import cn.yang37.chain.template.MessageChainSmsAli;
import cn.yang37.chain.template.MessageChainSmsTencentV3;
import cn.yang37.service.AbstractMessageService;
import cn.yang37.service.impl.DingTextMessageServiceImpl;
import cn.yang37.service.impl.SmsAliMessageServiceImpl;
import cn.yang37.service.impl.SmsTencentV3MessageServiceImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @class: NodeFactory
 * @author: yang37z@qq.com
 * @date: 2023/4/13 0:19
 * @version: 1.0
 */
public class MessageChainFactory {

    private volatile static MessageChainFactory messageChainFactory;

    private final Map<Class<? extends MessageChain>, MessageChain> clazz4Chain = new ConcurrentHashMap<>();

    private final Map<Class<? extends AbstractMessageService>, MessageChain> serviceClazz4Chain = new ConcurrentHashMap<>();


    /*
    映射chainClass到实例
     */ {
        clazz4Chain.put(MessageChainDing.class, new MessageChainDing());
        clazz4Chain.put(MessageChainSmsTencentV3.class, new MessageChainSmsTencentV3());
        clazz4Chain.put(MessageChainSmsAli.class, new MessageChainSmsAli());
    }

    /*
    映射service到chain
     */ {
        serviceClazz4Chain.put(DingTextMessageServiceImpl.class, getMessageChainByClass(MessageChainDing.class));
        serviceClazz4Chain.put(SmsTencentV3MessageServiceImpl.class, getMessageChainByClass(MessageChainSmsTencentV3.class));
        serviceClazz4Chain.put(SmsAliMessageServiceImpl.class, getMessageChainByClass(MessageChainSmsAli.class));
    }

    private MessageChainFactory() {
    }

    public static MessageChainFactory instance() {
        if (messageChainFactory == null) {
            synchronized (MessageChainFactory.class) {
                if (messageChainFactory == null) {
                    messageChainFactory = new MessageChainFactory();
                }
            }
        }
        return messageChainFactory;
    }

    public MessageChain getMessageChainByClass(Class<? extends MessageChain> clazz) {
        return clazz4Chain.get(clazz);
    }

    public MessageChain getMessageChain(Class<? extends AbstractMessageService> messageClazz) {
        return serviceClazz4Chain.get(messageClazz);
    }
}