package cn.yang37.util;

import cn.yang37.function.OgnlFunction;
import lombok.extern.slf4j.Slf4j;
import ognl.Ognl;

import java.util.function.Function;

/**
 * @description:
 * @class: OgnlUtils
 * @author: yang37z@qq.com
 * @date: 2024/5/10 14:41
 * @version: 1.0
 */
@Slf4j
public class OgnlUtils {

    /**
     * ognl函数受检异常转换
     *
     * @param ognlFunction OgnlFunction<T, R>
     * @param <T>          .
     * @param <R>          .
     * @return Function<T, R>
     */
    private static <T, R> Function<T, R> wrap(OgnlFunction<T, R> ognlFunction) {
        return arg -> {
            try {
                return ognlFunction.apply(arg);
            } catch (Exception e) {
                log.error("[OGNL] Failed to convert Ognl method to non-checked exception method!", e);
                throw new RuntimeException("OGNL error!");
            }
        };
    }

    public static Object getValue(String expression, Object root) {
        return wrap(value -> Ognl.getValue(expression, value)).apply(root);
    }

    public static Object getValue(String expression, Object root, Class<?> resultType) {
        return wrap(value -> Ognl.getValue(expression, value, resultType)).apply(root);
    }

}