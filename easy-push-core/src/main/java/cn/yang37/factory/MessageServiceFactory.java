package cn.yang37.factory;


import cn.yang37.chain.example.MessageChain;
import cn.yang37.service.execute.AbstractMessageService;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 消息服务工厂
 * @class: MessageServiceFactory
 * @author: yang37z@qq.com
 * @date: 2023/1/12 15:11
 * @version: 1.0
 */
public class MessageServiceFactory {

    private static final Logger log = LoggerFactory.getLogger(MessageServiceFactory.class);

    private static final MessageServiceFactory EXECUTE_MESSAGE_SERVICE_FACTORY = new MessageServiceFactory();

    /**
     * 服务对象
     */
    private final Map<Class<? extends AbstractMessageService>, AbstractMessageService> cacheServiceMap = new HashMap<>();

    private MessageServiceFactory() {
    }

    public static MessageServiceFactory instance() {
        return EXECUTE_MESSAGE_SERVICE_FACTORY;
    }

    /**
     * 返回消息服务对象
     *
     * @param messageServiceClazz .
     * @return .
     * @throws IllegalAccessException .
     * @throws InstantiationException .
     */
    public AbstractMessageService obtainMessageService(Class<? extends AbstractMessageService> messageServiceClazz) throws IllegalAccessException, InstantiationException {
        // 从对象池中获取消息服务对象
        AbstractMessageService abstractMessageService = cacheServiceMap.get(messageServiceClazz);

        if (ObjectUtils.isEmpty(abstractMessageService)) {
            // 构建Service实例
            AbstractMessageService instance = messageServiceClazz.newInstance();

            bindMessageChain(messageServiceClazz, instance);

            // 填充对象池
            cacheServiceMap.put(messageServiceClazz, instance);
            return instance;
        }

        return abstractMessageService;
    }

    private static void bindMessageChain(Class<? extends AbstractMessageService> messageServiceClazz, AbstractMessageService instance) throws IllegalAccessException, InstantiationException {
        // 绑定一个chain对象
        MessageChain messageChain = MessageChainStrategyFactory.instance().obtainMessageChainStrategy().getMessageChain(messageServiceClazz);
        instance.setMessageChain(messageChain);
        log.debug("[{}]绑定职责链: {}", messageServiceClazz, messageChain);
    }
}