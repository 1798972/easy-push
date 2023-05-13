package cn.yang37.strategy.service;

import cn.yang37.enums.MessageSceneType;
import cn.yang37.service.execute.AbstractMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

/**
 * @description: proxy
 * @class: ProxyMessageExecuteStrategyFactory
 * @author: yang37z@qq.com
 * @date: 2023/1/12 18:45
 * @version: 1.0
 */
public class ProxyMessageServiceStrategy extends AbstractMessageServiceStrategy {

    private static final Logger log = LoggerFactory.getLogger(ProxyMessageServiceStrategy.class);

    private final RealMessageServiceStrategy realMessageServiceStrategy;

    public ProxyMessageServiceStrategy(RealMessageServiceStrategy realMessageServiceStrategy) {
        this.realMessageServiceStrategy = realMessageServiceStrategy;
    }

    /**
     * 注意是取用代理对象的strategyMap,而非自身strategyMap.
     *
     * @param messageSceneType 目的地类型
     * @return .
     */
    @Override
    public AbstractMessageService getMessageService(MessageSceneType messageSceneType) {
        return realMessageServiceStrategy.getMessageService(messageSceneType);
    }

    @Override
    @PostConstruct
    protected void init() throws IllegalAccessException, InstantiationException {
        realMessageServiceStrategy.init();
        realMessageServiceStrategy.classInitBean();
        realMessageServiceStrategy.showInitInfo();
    }
}