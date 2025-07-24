package cn.yang37.chain.registry;

import cn.yang37.chain.MessageChain;
import cn.yang37.chain.NodeInterceptor;
import cn.yang37.chain.node.adapter.MessageNodeAdapter;
import cn.yang37.entity.context.MessageContext;

import java.util.*;

public class NodeEnhanceRegistry {

    /**
     * 结构：chain -> node -> List<interceptor>
     */
    private static final Map<Class<? extends MessageChain>, Map<Class<? extends MessageNodeAdapter>, List<NodeInterceptor>>> REGISTRY = new HashMap<>();

    public static void register(Class<? extends MessageChain> chainClass,
                                Class<? extends MessageNodeAdapter> nodeClass,
                                NodeInterceptor interceptor) {
        REGISTRY.computeIfAbsent(chainClass, k -> new HashMap<>())
                .computeIfAbsent(nodeClass, k -> new ArrayList<>())
                .add(interceptor);
    }

    /**
     * 可选lambda重载
     */
    public static void register(Class<? extends MessageChain> chainClass,
                                Class<? extends MessageNodeAdapter> nodeClass,
                                QuadConsumer<Class<? extends MessageChain>, Class<? extends MessageNodeAdapter>, MessageNodeAdapter, MessageContext> before,
                                QuadConsumer<Class<? extends MessageChain>, Class<? extends MessageNodeAdapter>, MessageNodeAdapter, MessageContext> after) {
        register(chainClass, nodeClass, new NodeInterceptor() {
            @Override
            public void beforeNode(Class<? extends MessageChain> c, Class<? extends MessageNodeAdapter> n, MessageNodeAdapter node, MessageContext ctx) {
                if (before != null) {
                    before.accept(c, n, node, ctx);
                }
            }

            @Override
            public void afterNode(Class<? extends MessageChain> c, Class<? extends MessageNodeAdapter> n, MessageNodeAdapter node, MessageContext ctx) {
                if (after != null) {
                    after.accept(c, n, node, ctx);
                }
            }
        });
    }

    public static List<NodeInterceptor> getInterceptors(Class<? extends MessageChain> chainClass, Class<? extends MessageNodeAdapter> nodeClass) {
        return REGISTRY.getOrDefault(chainClass, Collections.emptyMap()).getOrDefault(nodeClass, Collections.emptyList());
    }

    /**
     * 辅助函数（四参Consumer接口）
     *
     * @param <A>
     * @param <B>
     * @param <C>
     * @param <D>
     */
    @FunctionalInterface
    public interface QuadConsumer<A, B, C, D> {
        void accept(A a, B b, C c, D d);
    }
}
