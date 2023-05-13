package cn.yang37.chain.example;

import cn.yang37.chain.node.MessageNodeAdapter;
import cn.yang37.chain.node.def.DefaultEndNode;
import cn.yang37.chain.node.def.DefaultInitNode;
import cn.yang37.entity.MessageContext;
import cn.yang37.factory.MessageNodeFactory;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;

/**
 * @description:
 * @class: MessageChain
 * @author: yang37z@qq.com
 * @date: 2023/1/13 10:07
 * @version: 1.0
 */
public abstract class MessageChain {

    private static final Logger log = LoggerFactory.getLogger(MessageChain.class);

    protected static int nodeNum = 1;

    protected List<MessageNodeAdapter> nodeList = new LinkedList<>();

    protected List<Class<? extends MessageNodeAdapter>> nodeClassList = new LinkedList<>();

    private boolean addDefaultNode = true;

    /**
     * 子类中对节点进行排序
     */
    protected abstract void linkedNode() throws IllegalAccessException, InstantiationException;

    public MessageContext execute(MessageContext messageContext) throws IllegalAccessException, InstantiationException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, InvocationTargetException, NoSuchMethodException, ConfigurationException {
        // 预处理
        MessageContext preferDealContext = chainPreferDeal(messageContext);

        // 实际处理
        messageContext = chainExecuteDeal(preferDealContext);

        // 后处理
        return chainEndDeal(messageContext);
    }

    private MessageContext chainExecuteDeal(MessageContext messageContext) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, ConfigurationException {
        for (MessageNodeAdapter node : nodeList) {
            if (ObjectUtils.isNotEmpty(node)) {
                messageContext = node.nodeSingleSend(messageContext);
            } else {
                break;
            }
        }
        return messageContext;
    }

    private void class2Bean() {
        nodeClassList.forEach(clazz -> {
            try {
                nodeList.add(MessageNodeFactory.instance().obtainMessageNode(clazz));
            } catch (Exception e) {
                log.error("职责链中填充Node对象异常!", e);
            }
        });
    }


    private MessageContext chainPreferDeal(MessageContext messageContext) throws IllegalAccessException, InstantiationException {

        // 首次执行时进行link
        if (ObjectUtils.isEmpty(nodeList)) {
            // 链接Node
            linkedNode();

            // 添加默认首尾节点
            if (addDefaultNode) {
                nodeClassList.add(0, DefaultInitNode.class);
                nodeClassList.add(DefaultEndNode.class);
            }

            // class对象初始化
            class2Bean();
        }

        // 展示节点信息
        nodeList.forEach(e -> log.debug("[{}][Node]-[{}][{}]", this.getClass().getSimpleName(), nodeNum++, e.getClass()));

        return messageContext;
    }

    private MessageContext chainEndDeal(MessageContext messageContext) {
        nodeNum = 1;
        return messageContext;
    }

}