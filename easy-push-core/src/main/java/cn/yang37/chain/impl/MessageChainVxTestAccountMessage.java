package cn.yang37.chain.impl;

import cn.yang37.chain.MessageChain;
import cn.yang37.chain.node.vx.test.VxTestAccountAccessTokenNode;
import cn.yang37.chain.node.vx.test.VxTestAccountParamNode;
import cn.yang37.chain.node.vx.test.VxTestAccountSendNode;

/**
 * @description:
 * @class: MeeageChain
 * @author: yang37z@qq.com
 * @date: 2023/1/13 9:54
 * @version: 1.0
 */
public class MessageChainVxTestAccountMessage extends MessageChain {

    @Override
    protected void initNode() {
        nodeClassList.add(VxTestAccountParamNode.class);
        nodeClassList.add(VxTestAccountAccessTokenNode.class);
        nodeClassList.add(VxTestAccountSendNode.class);
    }
}