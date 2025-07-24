package cn.yang37.easypush.core.chain;

import cn.yang37.easypush.core.chain.node.adapter.MessageNodeAdapter;
import cn.yang37.easypush.entity.context.MessageContext;

/**
 * @description:
 * @class: ChainInterceptor
 * @author: yang37z@qq.com
 * @date: 2025/7/20 4:49
 * @version: 1.0
 */
public interface ChainInterceptor {

    /**
     * 节点执行前置拦截点（可以变更context、终止、打日志等）
     */
    default void beforeNode(MessageChain chain, MessageNodeAdapter node, MessageContext ctx) {
    }

    /**
     * 节点执行后置拦截点
     */
    default void afterNode(MessageChain chain, MessageNodeAdapter node, MessageContext ctx) {
    }
}