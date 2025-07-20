package cn.yang37.chain;

import cn.yang37.chain.node.adapter.MessageNodeAdapter;
import cn.yang37.chain.node.common.CommonEndNode;
import cn.yang37.chain.node.common.CommonInitNode;
import cn.yang37.chain.registry.ChainEnhanceRegistry;
import cn.yang37.chain.registry.NodeEnhanceRegistry;
import cn.yang37.chain.registry.NodeInsertRegistry;
import cn.yang37.entity.context.MessageContext;
import cn.yang37.entity.context.ThreadContext;
import cn.yang37.exception.ExecuteException;
import cn.yang37.factory.MessageNodeFactory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Comparator;
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
@Slf4j
public abstract class MessageChain {

    protected static AtomicInteger nodeNum = new AtomicInteger(1);

    /**
     * 责任链节点实例
     */
    protected List<MessageNodeAdapter> nodeList = new LinkedList<>();
    /**
     * 责任链节点类型
     */
    protected List<Class<? extends MessageNodeAdapter>> nodeClassList = new LinkedList<>();

    /**
     * 子类中声明实际NodeClass节点
     */
    protected abstract void initNode() throws IllegalAccessException, InstantiationException;

    /**
     * 责任链入口
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
     * Node处理
     */
    private MessageContext chainExecute(MessageContext messageContext) throws Exception {
        for (MessageNodeAdapter node : nodeList) {
            if (ObjectUtils.isNotEmpty(node)) {
                Class<? extends MessageNodeAdapter> nodeClass = node.getClass();
                Class<? extends MessageChain> chainClass = this.getClass();

                // 1. 全局链路增强
                for (ChainInterceptor ci : ChainEnhanceRegistry.all()) {
                    ci.beforeNode(this, node, messageContext);
                }

                // 2. 只调用“当前链+节点”已注册的NodeInterceptor
                for (NodeInterceptor ni : NodeEnhanceRegistry.getInterceptors(chainClass, nodeClass)) {
                    ni.beforeNode(chainClass, nodeClass, node, messageContext);
                }

                messageContext = node.nodeSingleSend(messageContext);

                for (NodeInterceptor ni : NodeEnhanceRegistry.getInterceptors(chainClass, nodeClass)) {
                    ni.afterNode(chainClass, nodeClass, node, messageContext);
                }
                for (ChainInterceptor ci : ChainEnhanceRegistry.all()) {
                    ci.afterNode(this, node, messageContext);
                }
            } else {
                break;
            }
        }
        return messageContext;
    }

    /**
     * 节点链初始化,支持注解+用户插拔
     */
    private MessageContext chainPreDeal(MessageContext messageContext) throws IllegalAccessException, InstantiationException {
        if (ObjectUtils.isEmpty(nodeList)) {
            // 1. 初始化标准节点链（注解/重写initNode/自定义收集均可）
            initNode();

            // 2. 插入用户自定义Node
            insertUserNode();

            // 3. 添加默认首尾节点
            addDefNode();

            // 4. 初始化节点实例
            nodeClass2NodeBean();
        }
        nodeList.forEach(e -> log.debug("[{}][Node]-[{}][{}]", this.getClass().getSimpleName(), nodeNum.getAndDecrement(), e.getClass()));
        return messageContext;
    }

    /**
     * 插入用户节点
     */
    private void insertUserNode() {
        List<NodeInsertRegistry.NodeInsertRequest> requests = NodeInsertRegistry.getInsertRequests(this.getClass());
        if (!requests.isEmpty()) {
            // 排序 如果有多个先按index升序插入 避免错位
            requests.sort(Comparator.comparingInt(r -> r.index));
            for (NodeInsertRegistry.NodeInsertRequest req : requests) {
                int idx = req.index;
                // 下标容错：不能越界
                if (idx < 0) {
                    idx = 0;
                }
                if (idx > nodeClassList.size()) {
                    idx = nodeClassList.size();
                }
                nodeClassList.add(idx, req.nodeClass);
            }
        }
    }

    private MessageContext chainEndDeal(MessageContext messageContext) {
        nodeNum = new AtomicInteger(1);
        ThreadContext.clean();
        return messageContext;
    }

    private void addDefNode() {
        nodeClassList.add(0, CommonInitNode.class);
        nodeClassList.add(CommonEndNode.class);
    }

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
