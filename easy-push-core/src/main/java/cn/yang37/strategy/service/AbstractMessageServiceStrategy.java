package cn.yang37.strategy.service;

import cn.yang37.enums.MessageSceneType;
import cn.yang37.factory.MessageServiceFactory;
import cn.yang37.service.execute.AbstractMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 消息服务策略, 聚合消息服务.
 * @class: AbstractMessageServiceStrategy
 * @author: yang37z@qq.com
 * @date: 2023/1/12 18:42
 * @version: 1.0
 */
public abstract class AbstractMessageServiceStrategy {

    private static final Logger log = LoggerFactory.getLogger(AbstractMessageServiceStrategy.class);

    private final static MessageServiceFactory SERVICE_FACTORY_INSTANCE = MessageServiceFactory.instance();

    /**
     * 根据场景维护实际处理Service
     */
    protected final Map<MessageSceneType, AbstractMessageService> serviceMap = new HashMap<>();

    /**
     * 根据场景维护实际处理Service的class类型
     */
    protected final Map<MessageSceneType, Class<? extends AbstractMessageService>> serviceClassMap = new HashMap<>();

    /**
     * 必须重写init方法来加载实际的策略对象
     *
     * @throws IllegalAccessException .
     * @throws InstantiationException .
     */
    protected abstract void init() throws IllegalAccessException, InstantiationException;


    /**
     * 根据消息场景返回实际的消息对象
     */
    public AbstractMessageService getMessageService(MessageSceneType messageSceneType) {
        return serviceMap.get(messageSceneType);
    }


    /**
     * 填充serviceMap
     */
    protected void classInitBean() {
        serviceClassMap.forEach((key, className) -> {
            try {
                serviceMap.put(key, SERVICE_FACTORY_INSTANCE.obtainMessageService(className));
            } catch (Exception e) {
                log.error("策略中填充[消息服务对象]时,填充异常!", e);
            }
        });
    }

    protected void showInitInfo() {
        log.debug("---------------------------------------------------------------");
        log.debug("[MessageService][策略] - strategyMap,Size: {}", serviceMap.size());

        serviceMap.forEach((k, v) -> {
            log.debug("[MessageService][策略] - strategyMap,Info:  {} -> {}", k, v);
        });
        log.debug("---------------------------------------------------------------");
    }
}