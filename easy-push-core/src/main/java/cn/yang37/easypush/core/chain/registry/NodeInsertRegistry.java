package cn.yang37.easypush.core.chain.registry;

import cn.yang37.easypush.core.chain.MessageChain;
import cn.yang37.easypush.core.chain.node.adapter.MessageNodeAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @class: NodeInsertRegistry
 * @author: yang37z@qq.com
 * @date: 2025/7/20 5:04
 * @version: 1.0
 */
public class NodeInsertRegistry {
    /**
     * 平台全局注册信息: Chain类型-> List<NodeInsertRequest>
     */
    private static final Map<Class<? extends MessageChain>, List<NodeInsertRequest>> REGISTRY = new ConcurrentHashMap<>();

    /**
     * 用户自定义Node
     *
     * @param chainClass      chain类型
     * @param customNodeClass 自定义Node
     * @param insertIndex     插入位置
     */
    public static void register(Class<? extends MessageChain> chainClass,
                                Class<? extends MessageNodeAdapter> customNodeClass,
                                int insertIndex) {
        REGISTRY.computeIfAbsent(chainClass, k -> new ArrayList<>())
                .add(new NodeInsertRequest(customNodeClass, insertIndex));
    }

    public static List<NodeInsertRequest> getInsertRequests(Class<? extends MessageChain> chainClass) {
        return REGISTRY.getOrDefault(chainClass, Collections.emptyList());
    }

    public static class NodeInsertRequest {
        public final Class<? extends MessageNodeAdapter> nodeClass;
        public final int index;

        public NodeInsertRequest(Class<? extends MessageNodeAdapter> nodeClass, int index) {
            this.nodeClass = nodeClass;
            this.index = index;
        }
    }
}