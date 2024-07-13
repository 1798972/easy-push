package cn.yang37.chain.node.adapter;


import cn.yang37.chain.node.MessageNode;
import cn.yang37.factory.ConfigFactory;
import cn.yang37.util.MessageProvider;

/**
 * @description: 节点
 * @class: MessageHandler
 * @author: yang37z@qq.com
 * @date: 2023/1/11 11:34
 * @version: 1.0
 */
public abstract class MessageNodeAdapter extends MessageProvider implements MessageNode {

    protected final ConfigFactory configFactory = ConfigFactory.instance();

}