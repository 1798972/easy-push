package cn.yang37.chain.template;

import cn.yang37.chain.MessageChain;
import cn.yang37.chain.node.ding.DingTextCheckNode;
import cn.yang37.chain.node.ding.DingTextContentNode;
import cn.yang37.chain.node.ding.DingTextSendNode;

/**
 * @description:
 * @class: MeeageChain
 * @author: yang37z@qq.com
 * @date: 2023/1/13 9:54
 * @version: 1.0
 */
public class MessageChainDing extends MessageChain {

    @Override
    protected void initNode() {
        nodeClassList.add(DingTextCheckNode.class);
        nodeClassList.add(DingTextContentNode.class);
        nodeClassList.add(DingTextSendNode.class);
    }
}