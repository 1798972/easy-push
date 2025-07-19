package cn.yang37.chain;

import cn.yang37.chain.node.adapter.MessageNodeAdapter;
import cn.yang37.entity.context.MessageContext;

/**
 * @description:
 * @class: NodeInterceptor
 * @author: yang37z@qq.com
 * @date: 2025/7/20 5:19
 * @version: 1.0
 */
public interface NodeInterceptor {
    /**
     * @param chainClass 责任链类型
     * @param nodeClass  节点类型
     * @param node       节点实例(用户自定义)
     * @param ctx        上下文
     */
    default void beforeNode(Class<? extends MessageChain> chainClass, Class<? extends MessageNodeAdapter> nodeClass, MessageNodeAdapter node, MessageContext ctx) {
    }

    /**
     * @param chainClass 责任链类型
     * @param nodeClass  节点类型
     * @param node       节点实例(用户自定义)
     * @param ctx        上下文
     */
    default void afterNode(Class<? extends MessageChain> chainClass, Class<? extends MessageNodeAdapter> nodeClass, MessageNodeAdapter node, MessageContext ctx) {
    }
}
