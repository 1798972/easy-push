package cn.yang37.chain.impl;

import cn.yang37.chain.MessageChain;
import cn.yang37.chain.node.sms.ali.v3.SmsAliV3ParamNode;
import cn.yang37.chain.node.sms.ali.v3.SmsAliV3SendNode;
import cn.yang37.chain.node.sms.ali.v3.SmsAliV3SignNode;

/**
 * @description:
 * @class: MessageChainSmsTencent
 * @author: yang37z@qq.com
 * @date: 2023/9/5 23:11
 * @version: 1.0
 */
public class MessageChainSmsAli extends MessageChain {

    @Override
    protected void initNode() {
        nodeClassList.add(SmsAliV3ParamNode.class);
        nodeClassList.add(SmsAliV3SignNode.class);
        nodeClassList.add(SmsAliV3SendNode.class);
    }
}