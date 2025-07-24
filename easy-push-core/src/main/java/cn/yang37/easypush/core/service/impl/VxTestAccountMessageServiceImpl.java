package cn.yang37.easypush.core.service.impl;

import cn.yang37.easypush.core.annotation.EpMessageChain;
import cn.yang37.easypush.core.annotation.EpMessageScene;
import cn.yang37.easypush.core.chain.impl.MessageChainVxTestAccountMessage;
import cn.yang37.easypush.core.service.AbstractMessageService;
import cn.yang37.easypush.entity.enums.MessageSceneType;

/**
 * @description:
 * @class: SmsTencentMessageServiceImpl
 * @author: yang37z@qq.com
 * @date: 2023/9/5 23:07
 * @version: 1.0
 */
@EpMessageScene(MessageSceneType.VX_TEST_ACCOUNT)
@EpMessageChain(MessageChainVxTestAccountMessage.class)
public class VxTestAccountMessageServiceImpl extends AbstractMessageService {
}