package cn.yang37.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Properties;

/**
 * @description:
 * @class: ConfigUtils
 * @author: yang37z@qq.com
 * @date: 2024/5/21 4:40
 * @version: 1.0
 */
public class ConfigUtils {

    public static <T> T populate(Class<T> clazz, Properties properties, String prefix) throws Exception {
        T instance = clazz.getDeclaredConstructor().newInstance();

        for (Field field : clazz.getDeclaredFields()) {
            // 检查字段是否为非静态字段
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            String propertyName = convertToHyphenCase(field.getName());
            String propertyValue = properties.getProperty(propertyName);
            if (propertyValue != null) {
                field.setAccessible(true);
                field.set(instance, convertValue(field.getType(), propertyValue));
            }
        }

        return instance;
    }

    private static Object convertValue(Class<?> targetType, String value) {
        if (targetType == String.class) {
            return value;
        } else if (targetType == int.class || targetType == Integer.class) {
            return Integer.parseInt(value);
        } else if (targetType == long.class || targetType == Long.class) {
            return Long.parseLong(value);
        } else if (targetType == boolean.class || targetType == Boolean.class) {
            return Boolean.parseBoolean(value);
        } else if (targetType == double.class || targetType == Double.class) {
            return Double.parseDouble(value);
        } else if (targetType == float.class || targetType == Float.class) {
            return Float.parseFloat(value);
        }

        // 添加更多类型转换
        throw new IllegalArgumentException("Unsupported target type: " + targetType);
    }

    private static String convertToHyphenCase(String camelCase) {
        StringBuilder result = new StringBuilder();
        for (char c : camelCase.toCharArray()) {
            if (Character.isUpperCase(c)) {
                result.append('-').append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}