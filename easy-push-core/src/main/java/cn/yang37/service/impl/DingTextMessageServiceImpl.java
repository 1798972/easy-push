package cn.yang37.service.impl;


import cn.yang37.annotation.MessageChain;
import cn.yang37.annotation.MessageScene;
import cn.yang37.chain.impl.MessageChainDing;
import cn.yang37.enums.MessageSceneType;
import cn.yang37.service.AbstractMessageService;

/**
 * @description: 钉钉-文本服务
 * @class: SendServiceImpl
 * @author: yang37z@qq.com
 * @date: 2023/1/12 15:13
 * @version: 1.0
 */
@MessageScene(MessageSceneType.DING_TEXT)
@MessageChain(MessageChainDing.class)
public class DingTextMessageServiceImpl extends AbstractMessageService {
}