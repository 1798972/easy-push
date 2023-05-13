package cn.yang37.factory;


import cn.yang37.strategy.service.AbstractMessageServiceStrategy;
import cn.yang37.strategy.service.ProxyMessageServiceStrategy;
import cn.yang37.strategy.service.RealMessageServiceStrategy;
import org.apache.commons.lang3.ObjectUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 消息服务, 策略工厂.
 * @class: ExecuteMessageServiceFactory
 * @author: yang37z@qq.com
 * @date: 2023/1/12 15:11
 * @version: 1.0
 */
public class MessageServiceStrategyFactory {

    private static final MessageServiceStrategyFactory MESSAGE_EXECUTE_FACTORY = new MessageServiceStrategyFactory();

    private final Map<Class<? extends AbstractMessageServiceStrategy>, AbstractMessageServiceStrategy> cacheBeanMap = new HashMap<>();

    private MessageServiceStrategyFactory() {
    }

    public static MessageServiceStrategyFactory instance() {
        return MESSAGE_EXECUTE_FACTORY;
    }

    /**
     * @param implClass class
     * @return 服务对应的策略
     * @throws IllegalAccessException    .
     * @throws InstantiationException    .
     * @throws InvocationTargetException .
     */
    public AbstractMessageServiceStrategy obtainMessageServiceStrategy(Class<? extends AbstractMessageServiceStrategy> implClass) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        // 直接从缓存中获取
        AbstractMessageServiceStrategy executeStrategy = cacheBeanMap.get(implClass);

        // 为空时,通过方法创建并获取
        if (ObjectUtils.isEmpty(executeStrategy)) {
            if (ProxyMessageServiceStrategy.class == implClass) {
                return createProxyMessageExecuteStrategy(implClass);
            }
            return createOrdinaryMessageExecuteStrategy(implClass);
        }
        return executeStrategy;
    }

    /**
     * 返回代理消息策略对象
     */
    private AbstractMessageServiceStrategy createProxyMessageExecuteStrategy(Class<? extends AbstractMessageServiceStrategy> implClass) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        RealMessageServiceStrategy realMessageExecuteStrategyFactory = (RealMessageServiceStrategy) obtainMessageServiceStrategy(RealMessageServiceStrategy.class);
        ProxyMessageServiceStrategy proxyMessageExecuteStrategy = new ProxyMessageServiceStrategy(realMessageExecuteStrategyFactory);

        afterCreate(implClass, proxyMessageExecuteStrategy);
        return proxyMessageExecuteStrategy;
    }


    /**
     * 返回消息策略对象
     */
    private AbstractMessageServiceStrategy createOrdinaryMessageExecuteStrategy(Class<? extends AbstractMessageServiceStrategy> implClass) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        AbstractMessageServiceStrategy instance = implClass.newInstance();
        afterCreate(implClass, instance);
        return instance;
    }

    /**
     * 对象创建后,1.执行init方法 2.放入对象池
     * @param implClass .
     * @param instance .
     * @throws IllegalAccessException .
     * @throws InvocationTargetException .
     */
    private void afterCreate(Class<? extends AbstractMessageServiceStrategy> implClass, AbstractMessageServiceStrategy instance) throws IllegalAccessException, InvocationTargetException {
        initPostConstruct(implClass, instance);
        cacheBeanMap.put(implClass, instance);
    }

    /**
     * 策略实例化前触发@PostConstruct方法
     *
     * @param implClass                      .
     * @param abstractMessageServiceStrategy .
     * @throws IllegalAccessException    .
     * @throws InvocationTargetException .
     */
    private static void initPostConstruct(Class<? extends AbstractMessageServiceStrategy> implClass, AbstractMessageServiceStrategy abstractMessageServiceStrategy) throws IllegalAccessException, InvocationTargetException {
        // @PostConstruct
        Method[] declaredMethods = implClass.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.isAnnotationPresent(PostConstruct.class)) {
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(abstractMessageServiceStrategy);
            }
        }
    }
}

