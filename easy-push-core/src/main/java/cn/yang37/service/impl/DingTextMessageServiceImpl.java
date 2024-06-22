package cn.yang37.service.impl;


import cn.yang37.factory.MessageChainFactory;
import cn.yang37.service.AbstractMessageService;

/**
 * @description: 钉钉-文本服务
 * @class: SendServiceImpl
 * @author: yang37z@qq.com
 * @date: 2023/1/12 15:13
 * @version: 1.0
 */
public class DingTextMessageServiceImpl extends AbstractMessageService {

    public DingTextMessageServiceImpl() {
        this.messageChain = MessageChainFactory.instance().getMessageChain(this.getClass());
    }

}