package cn.yang37.factory;

import cn.yang37.annotation.MessageChain;
import cn.yang37.annotation.MessageScene;
import cn.yang37.enums.MessageSceneType;
import cn.yang37.service.AbstractMessageService;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class MessageServiceFactory {

    private static final String PACKAGE_PATH = "cn.yang37.service.impl";

    private static final String NAME_DI_FILED = "messageChain";

    /**
     * MessageSceneType -> MessageService实例
     */
    private final Map<MessageSceneType, AbstractMessageService> scene4ServicePool = new ConcurrentHashMap<>();

    private MessageServiceFactory() {
        // 自动注册
        registerAnnotatedServices();
    }

    /**
     * 自动扫描指定包下所有带注解的 Service 实现类
     */
    private void registerAnnotatedServices() {
        // 扫描服务类实例
        Set<Class<? extends AbstractMessageService>> serviceClasses = new Reflections(PACKAGE_PATH).getSubTypesOf(AbstractMessageService.class);

        for (Class<? extends AbstractMessageService> clazz : serviceClasses) {
            MessageChain annotationChain = clazz.getAnnotation(MessageChain.class);
            MessageScene annotationScene = clazz.getAnnotation(MessageScene.class);
            if (annotationChain != null) {
                try {
                    // messageService实例化
                    AbstractMessageService service = clazz.getDeclaredConstructor().newInstance();
                    // service绑定chain对象
                    Class<? extends cn.yang37.chain.MessageChain> clazz4Chain = annotationChain.value();
                    // 获取chain实例
                    cn.yang37.chain.MessageChain chain = MessageChainFactory.instance().getMessageChain(clazz4Chain);
                    // 指定绑定
                    Field chainField = AbstractMessageService.class.getDeclaredField(NAME_DI_FILED);
                    chainField.setAccessible(true);
                    chainField.set(service, chain);
                    log.info("[对象依赖注入][MessageService][MessageService -> MessageChain] 注入成功,{} -> @{}", service.getClass().getName(), System.identityHashCode(chain));
                    // 根据场景初始化service
                    if (annotationScene != null) {
                        MessageSceneType sceneType = annotationScene.value();
                        scene4ServicePool.put(sceneType, service);
                        log.info("[对象池初始化][MessageService][MessageSceneType -> MessageService] 初始化成功,{} -> {}", sceneType, clazz);
                    }
                } catch (Exception e) {
                    log.error("[对象池初始化][MessageSceneType -> MessageService] 初始化失败,class: {}", clazz, e);
                }
            }
        }
    }

    private static class Holder {
        private static final MessageServiceFactory INSTANCE = new MessageServiceFactory();
    }

    public static MessageServiceFactory instance() {
        return Holder.INSTANCE;
    }

    public AbstractMessageService getMessageService(MessageSceneType messageSceneType) {
        return scene4ServicePool.get(messageSceneType);
    }

    public void registerScene(MessageSceneType type, AbstractMessageService instance) {
        scene4ServicePool.put(type, instance);
    }
}
