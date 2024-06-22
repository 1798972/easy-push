package cn.yang37.factory;


import cn.yang37.enums.MessageSceneType;
import cn.yang37.service.AbstractMessageService;
import cn.yang37.service.impl.DingTextMessageServiceImpl;
import cn.yang37.service.impl.SmsAliMessageServiceImpl;
import cn.yang37.service.impl.SmsTencentV3MessageServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 消息服务工厂
 * @class: MessageServiceFactory
 * @author: yang37z@qq.com
 * @date: 2023/1/12 15:11
 * @version: 1.0
 */
public class MessageServiceFactory {

    private static final Logger log = LoggerFactory.getLogger(MessageServiceFactory.class);

    private static volatile MessageServiceFactory messageServiceFactory;

    /**
     * class-service
     */
    private final Map<Class<? extends AbstractMessageService>, AbstractMessageService> class4ServiceMap = new HashMap<>();

    /**
     * scene-service
     */
    protected final Map<MessageSceneType, AbstractMessageService> scene4ServicePool = new ConcurrentHashMap<>();

    /*
     sceneClass-service
     */ {
        class4ServiceMap.put(DingTextMessageServiceImpl.class, new DingTextMessageServiceImpl());
        class4ServiceMap.put(SmsTencentV3MessageServiceImpl.class, new SmsTencentV3MessageServiceImpl());
        class4ServiceMap.put(SmsAliMessageServiceImpl.class, new SmsAliMessageServiceImpl());
    }

    /*
     sceneClass-service
     */ {
        scene4ServicePool.put(MessageSceneType.DING, getMessageService(DingTextMessageServiceImpl.class));
        scene4ServicePool.put(MessageSceneType.SMS_TENCENT_V3, getMessageService(SmsTencentV3MessageServiceImpl.class));
        scene4ServicePool.put(MessageSceneType.SMS_ALI_V3, getMessageService(SmsAliMessageServiceImpl.class));
    }

    private MessageServiceFactory() {
    }

    public static MessageServiceFactory instance() {
        if (messageServiceFactory == null) {
            synchronized (MessageServiceFactory.class) {
                if (messageServiceFactory == null) {
                    messageServiceFactory = new MessageServiceFactory();
                }
            }
        }
        return messageServiceFactory;
    }

    private AbstractMessageService getMessageService(Class<? extends AbstractMessageService> messageServiceClazz) {
        return class4ServiceMap.get(messageServiceClazz);
    }

    public AbstractMessageService getMessageService(MessageSceneType messageSceneType) {
        return scene4ServicePool.get(messageSceneType);
    }


}