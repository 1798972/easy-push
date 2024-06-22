package cn.yang37.util;

import cn.yang37.entity.context.ThreadContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.MDC;

import java.util.UUID;

public class TraceUtils {


    private static final String SPLIT_APPEND = ">";

    public static final String TRACE_ID = "traceId";

    public static final String PRE_MSG = "preMsg";

    /**
     * 自动生成traceId
     */
    public static void traceStart() {
        String traceId = generateTraceId();
        ThreadContext.init();

        MDC.put(TRACE_ID, traceId);
        ThreadContext.putTraceId(traceId);
    }

    /**
     * 输入traceId,并填充preMsg
     */
    public static void traceStart(String traceId, String preMsg) {
        MDC.put(PRE_MSG, preMsg);
        traceStartTraceId(traceId);
    }

    /**
     * 自动生成traceId,并填充preMsg
     */
    public static void traceStartPreMsg(String preMsg) {
        traceStart();
        MDC.put(PRE_MSG, preMsg);
    }

    /**
     * 输入traceId
     */
    public static void traceStartTraceId(String traceId) {
        ThreadContext.init();

        MDC.put(TRACE_ID, traceId);
        ThreadContext.putTraceId(traceId);
    }

    /**
     * 追踪结束
     */
    public static void traceEnd() {
        MDC.clear();
        ThreadContext.clean();
    }

    /**
     * 重新设置值
     */
    public static void resetTraceId(String traceId) {
        if (traceId.length() >= 33 * 8) {
            traceId = traceId.substring(0, 33 * 8);
        }
        MDC.put(TRACE_ID, traceId);
    }

    /**
     * 追加traceId
     * xxx -> xxx>yyy
     * 若当前traceId为空,则直接使用yyy
     */
    public static void appendTraceId() {
        // 获取当前traceId
        String nowTraceId = TraceUtils.getTraceId();
        String appendId = TraceUtils.generateTraceId();

        // 变更后
        String after = StringUtils.isEmpty(nowTraceId) ? appendId : nowTraceId + SPLIT_APPEND + appendId;
        TraceUtils.resetTraceId(after);
    }

    /**
     * 更新preMsg
     */
    public static void setPreMsg(String preMsg) {
        MDC.put(PRE_MSG, preMsg);
    }

    /**
     * 追加traceId,并更新PRE_MSG
     * xxx -> xxx>yyy
     */
    public static void appendTraceId(String preMsg) {
        MDC.put(PRE_MSG, preMsg);
        appendTraceId();
    }

    /**
     * 根据规则回退TraceId
     * xxx>yyyy -> xxx
     */
    public static void reductionTraceId() {
        // 获取当前traceId
        String nowTraceId = TraceUtils.getTraceId();
        if (StringUtils.isNotEmpty(nowTraceId)) {
            int lastIndex = nowTraceId.lastIndexOf(SPLIT_APPEND);
            if (lastIndex != -1) {
                resetTraceId(nowTraceId.substring(0, lastIndex));
            } else {
                resetTraceId(nowTraceId);
            }
        }
    }

    /**
     * 复原traceId
     */
    public static void recoverTraceId() {
        // 获取当前traceId
        String nowTraceId = TraceUtils.getTraceId();

        int firstIndex = nowTraceId.indexOf(SPLIT_APPEND);
        if (firstIndex != -1) {
            resetTraceId(nowTraceId.substring(0, firstIndex));
        } else {
            resetTraceId(nowTraceId);
        }
    }

    /**
     * 复原traceId并清空preMsg
     */
    public static void recoverTraceIdAndCleanPreMsg() {
        cleanPreMsg();
        recoverTraceId();
    }

    /**
     * 清空
     */
    public static void cleanPreMsg() {
        MDC.put(PRE_MSG, "");
    }

    /**
     * 生成跟踪ID
     *
     * @return .
     */
    public static String generateTraceId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取值
     *
     * @return .
     */
    public static String getTraceId() {
        return MDC.get(TRACE_ID);
    }

    /**
     * 获取末尾的traceId
     *
     * @return .
     */
    public static String getTraceIdLast() {
        String[] parts = TraceUtils.getTraceId().split(">");
        if (parts.length == 0) {
            return "";
        }

        return parts[parts.length - 1];
    }

}
