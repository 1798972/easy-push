package cn.yang37.chain.registry;

import cn.yang37.chain.ChainInterceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @class: ChainInterceptorRegistry
 * @author: yang37z@qq.com
 * @date: 2025/7/20 5:12
 * @version: 1.0
 */
public class ChainEnhanceRegistry {

    /**
     * Node拦截器列表
     */
    private static final List<ChainInterceptor> INTERCEPTOR_LIST = new ArrayList<>();

    public static void register(ChainInterceptor interceptor) {
        INTERCEPTOR_LIST.add(interceptor);
    }

    public static List<ChainInterceptor> all() {
        return INTERCEPTOR_LIST;
    }
}