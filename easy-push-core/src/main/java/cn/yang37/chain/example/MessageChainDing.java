package cn.yang37.chain.example;

import cn.yang37.chain.node.ding.DingCheckNode;
import cn.yang37.chain.node.ding.DingContentNode;
import cn.yang37.chain.node.ding.DingSendNode;

/**
 * @description:
 * @class: MeeageChain
 * @author: yang37z@qq.com
 * @date: 2023/1/13 9:54
 * @version: 1.0
 */
public class MessageChainDing extends MessageChain {

    @Override
    protected void linkedNode() {
        nodeClassList.add(DingCheckNode.class);
        nodeClassList.add(DingContentNode.class);
        nodeClassList.add(DingSendNode.class);
    }
}