package cn.yang37.chain;

import cn.yang37.chain.node.adapter.MessageNodeAdapter;
import cn.yang37.chain.node.def.DefaultEndNode;
import cn.yang37.chain.node.def.DefaultInitNode;
import cn.yang37.entity.context.MessageContext;
import cn.yang37.entity.context.ThreadContext;
import cn.yang37.exception.ExecuteException;
import cn.yang37.factory.MessageNodeFactory;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description:
 * @class: MessageChain
 * @author: yang37z@qq.com
 * @date: 2023/1/13 10:07
 * @version: 1.0
 */
@Data
public abstract class MessageChain {

    private static final Logger log = LoggerFactory.getLogger(MessageChain.class);

    protected static AtomicInteger nodeNum = new AtomicInteger(1);

    protected List<MessageNodeAdapter> nodeList = new LinkedList<>();

    protected List<Class<? extends MessageNodeAdapter>> nodeClassList = new LinkedList<>();

    /**
     * 子类中声明实际NodeClass节点
     *
     * @throws IllegalAccessException .
     * @throws InstantiationException .
     */
    protected abstract void initNode() throws IllegalAccessException, InstantiationException;

    /**
     * 处理方法
     *
     * @param messageContext .
     * @return .
     */
    public MessageContext execute(MessageContext messageContext) {
        MessageContext resultMessageContext;
        try {
            resultMessageContext = chainEndDeal(chainExecute(chainPreDeal(messageContext)));
        } catch (Exception e) {
            throw new ExecuteException(e);
        } finally {
            ThreadContext.clean();
        }
        return resultMessageContext;
    }

    /**
     * 实际处理方法
     *
     * @param messageContext .
     * @return .
     * @throws Exception .
     */
    private MessageContext chainExecute(MessageContext messageContext) throws Exception {
        for (MessageNodeAdapter node : nodeList) {
            if (ObjectUtils.isNotEmpty(node)) {
                messageContext = node.nodeSingleSend(messageContext);
            } else {
                break;
            }
        }
        return messageContext;
    }


    /**
     * 预处理方法
     *
     * @param messageContext .
     * @return .
     * @throws IllegalAccessException .
     * @throws InstantiationException .
     */
    private MessageContext chainPreDeal(MessageContext messageContext) throws IllegalAccessException, InstantiationException {

        // 首次执行时进行link
        if (ObjectUtils.isEmpty(nodeList)) {
            // 链接Node
            initNode();

            // 添加默认处理节点
            addDefNode();

            // class对象初始化
            nodeClass2NodeBean();
        }

        // 展示节点信息
        nodeList.forEach(e -> log.debug("[{}][Node]-[{}][{}]", this.getClass().getSimpleName(), nodeNum.getAndDecrement(), e.getClass()));

        return messageContext;
    }

    /**
     * 后处理方法
     *
     * @param messageContext .
     * @return .
     */
    private MessageContext chainEndDeal(MessageContext messageContext) {
        nodeNum = new AtomicInteger(1);
        ThreadContext.clean();
        return messageContext;
    }

    /**
     * 添加默认节点
     */
    private void addDefNode() {
        // 添加默认首尾节点
        nodeClassList.add(0, DefaultInitNode.class);
        nodeClassList.add(DefaultEndNode.class);
    }

    /**
     * 添加node对象
     */
    private void nodeClass2NodeBean() {
        nodeClassList.forEach(clazz -> {
            try {
                nodeList.add(MessageNodeFactory.instance().getMessageNode(clazz));
            } catch (Exception e) {
                log.error("Node object filling exception in responsibility chain!", e);
            }
        });
    }
}