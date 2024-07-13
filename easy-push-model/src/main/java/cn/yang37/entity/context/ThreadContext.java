package cn.yang37.entity.context;

import java.util.HashMap;
import java.util.Map;

public class ThreadContext {


    /**
     * 记得clean,防止内存泄露.
     */
    private final static ThreadLocal<Map<String, Object>> CTX_HOLDER = new ThreadLocal<>();

    static {
        CTX_HOLDER.set(new HashMap<>());
    }

    /**
     * traceID
     */
    private final static String TRACE_ID_KEY = "traceId";


    /**
     * 初始化线程上下文
     */
    public static void init() {
        CTX_HOLDER.set(new HashMap<>());
    }

    /**
     * 清空线程上下文
     */
    public static void clean() {
        CTX_HOLDER.remove();
    }


    /**
     * 获取线程上下文
     */
    public static Map<String, Object> getContext() {
        return CTX_HOLDER.get();
    }

    /**
     * 添加内容到线程上下文中
     *
     * @param key
     * @param value
     */
    public static void putContext(String key, Object value) {
        Map<String, Object> ctx = CTX_HOLDER.get();
        if (ctx == null) {
            return;
        }
        ctx.put(key, value);
    }

    /**
     * 从线程上下文中获取内容
     *
     * @param key .
     */
    public static <T> T getContext(String key) {
        Map<String, Object> ctx = CTX_HOLDER.get();
        if (ctx == null) {
            return null;
        }
        return (T) ctx.get(key);
    }


    /**
     * 从线程上下文中获取内容
     */
    public static <T> T getContext(String key, Class<T> tClass) {
        Map<String, Object> ctx = CTX_HOLDER.get();
        if (null == ctx) {
            return null;
        }

        return tClass.cast(ctx.get(key));
    }

    /**
     * 删除上下文中的key
     *
     * @param key
     */
    public static void remove(String key) {
        Map<String, Object> ctx = CTX_HOLDER.get();
        if (ctx != null) {
            ctx.remove(key);
        }
    }

    /**
     * 上下文中是否包含此key
     *
     * @param key
     * @return
     */
    public static boolean contains(String key) {
        Map<String, Object> ctx = CTX_HOLDER.get();
        if (ctx != null) {
            return ctx.containsKey(key);
        }
        return false;
    }


    /**
     * 设置traceID数据
     */
    public static void putTraceId(String traceId) {
        putContext(TRACE_ID_KEY, traceId);
    }

    /**
     * 获取traceID数据
     */
    public static String getTraceId() {
        return getContext(TRACE_ID_KEY);
    }

}
