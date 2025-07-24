package cn.yang37.easypush.core.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONPath;
import com.alibaba.fastjson2.filter.NameFilter;

/**
 * @description:
 * @class: FastjsonUtils
 * @author: yang37z@qq.com
 * @date: 2025/7/19 18:03
 * @version: 1.0
 */
public class JsonUtils {

    /**
     * 首字母大写驼峰
     */
    private static final NameFilter UPPER_CAMEL = (object, name, value) ->
            name == null || name.isEmpty()
                    ? name
                    : name.substring(0, 1).toUpperCase() + name.substring(1);

    /**
     * 下划线
     */
    private static final NameFilter SNAKE_CASE = (object, name, value) ->
            name.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();

    /**
     * object -> json/首字母大写驼峰
     */
    public static String toUpperCamelJsonString(Object obj) {
        return JSON.toJSONString(obj, UPPER_CAMEL);
    }

    /**
     * object -> json/下划线
     */
    public static String toSnakeCaseJsonString(Object obj) {
        return JSON.toJSONString(obj, SNAKE_CASE);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getValue(String expression, Object obj, Class<T> clazz) {
        Object value = JSONPath.eval(obj, expression);
        if (value == null) {
            return null;
        }
        if (clazz.isInstance(value)) {
            return (T) value;
        }
        // 自动支持常见基础类型转换
        try {
            if (clazz == String.class) {
                return (T) value.toString();
            } else if (clazz == Integer.class) {
                if (value instanceof Number) {
                    return (T) Integer.valueOf(((Number) value).intValue());
                }
                return (T) Integer.valueOf(value.toString());
            } else if (clazz == Long.class) {
                if (value instanceof Number) {
                    return (T) Long.valueOf(((Number) value).longValue());
                }
                return (T) Long.valueOf(value.toString());
            } else if (clazz == Double.class) {
                if (value instanceof Number) {
                    return (T) Double.valueOf(((Number) value).doubleValue());
                }
                return (T) Double.valueOf(value.toString());
            } else if (clazz == Boolean.class) {
                if (value instanceof Boolean) {
                    return (T) value;
                }
                return (T) Boolean.valueOf(value.toString());
            }
        } catch (Exception ignore) {
            // 转换失败，返回null
        }
        return null;
    }
}
