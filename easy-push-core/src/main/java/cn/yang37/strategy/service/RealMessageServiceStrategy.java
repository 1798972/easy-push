package cn.yang37.strategy.service;

import cn.yang37.enums.MessageSceneType;
import cn.yang37.factory.MessageServiceFactory;
import cn.yang37.service.execute.impl.DingTextMessageServiceImpl;

/**
 * @description: real
 * @class: MessageExecuteStrategyFactory
 * @author: yang37z@qq.com
 * @date: 2023/1/12 18:51
 * @version: 1.0
 */
public class RealMessageServiceStrategy extends AbstractMessageServiceStrategy {

    private final static MessageServiceFactory SERVICE_FACTORY_INSTANCE = MessageServiceFactory.instance();

    @Override
    protected void init() {
        serviceClassMap.put(MessageSceneType.DING, DingTextMessageServiceImpl.class);
    }
}