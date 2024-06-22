package cn.yang37.function;

/**
 * @description:
 * @class: OgnlFunction
 * @author: yang37z@qq.com
 * @date: 2024/1/10 15:44
 * @version: 1.0
 */
@FunctionalInterface
public interface OgnlFunction<T, R> {

    R apply(T t) throws Exception;
}
