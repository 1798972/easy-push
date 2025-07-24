package cn.yang37.easypush.core.service.impl;


import cn.yang37.easypush.core.annotation.EpMessageChain;
import cn.yang37.easypush.core.annotation.EpMessageScene;
import cn.yang37.easypush.core.chain.impl.MessageChainDing;
import cn.yang37.easypush.core.service.AbstractMessageService;
import cn.yang37.easypush.entity.enums.MessageSceneType;

/**
 * @description: 钉钉-文本服务
 * @class: SendServiceImpl
 * @author: yang37z@qq.com
 * @date: 2023/1/12 15:13
 * @version: 1.0
 */
@EpMessageScene(MessageSceneType.DING_TEXT)
@EpMessageChain(MessageChainDing.class)
public class DingTextMessageServiceImpl extends AbstractMessageService {
}