package cn.yang37.service.impl;

import cn.yang37.annotation.MessageChain;
import cn.yang37.annotation.MessageScene;
import cn.yang37.chain.impl.MessageChainVxTestAccountMessage;
import cn.yang37.enums.MessageSceneType;
import cn.yang37.service.AbstractMessageService;

/**
 * @description:
 * @class: SmsTencentMessageServiceImpl
 * @author: yang37z@qq.com
 * @date: 2023/9/5 23:07
 * @version: 1.0
 */
@MessageScene(MessageSceneType.VX_TEST_ACCOUNT)
@MessageChain(MessageChainVxTestAccountMessage.class)
public class VxTestAccountMessageServiceImpl extends AbstractMessageService {
}