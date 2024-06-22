package cn.yang37.service.impl;

import cn.yang37.factory.MessageChainFactory;
import cn.yang37.service.AbstractMessageService;

/**
 * @description:
 * @class: SmsTencentMessageServiceImpl
 * @author: yang37z@qq.com
 * @date: 2023/9/5 23:07
 * @version: 1.0
 */
public class SmsAliMessageServiceImpl extends AbstractMessageService {

    public SmsAliMessageServiceImpl() {
        this.messageChain = MessageChainFactory.instance().getMessageChain(this.getClass());
    }
}