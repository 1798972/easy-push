package cn.yang37.chain.impl;

import cn.yang37.chain.MessageChain;
import cn.yang37.chain.node.sms.tencent.v3.SmsTencentV3ParamNode;
import cn.yang37.chain.node.sms.tencent.v3.SmsTencentV3SendNode;
import cn.yang37.chain.node.sms.tencent.v3.SmsTencentV3SignNode;

/**
 * @description:
 * @class: MessageChainSmsTencent
 * @author: yang37z@qq.com
 * @date: 2023/9/5 23:11
 * @version: 1.0
 */
public class MessageChainSmsTencentV3 extends MessageChain {

    @Override
    protected void initNode() {
        nodeClassList.add(SmsTencentV3ParamNode.class);
        nodeClassList.add(SmsTencentV3SignNode.class);
        nodeClassList.add(SmsTencentV3SendNode.class);
    }
}