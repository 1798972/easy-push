package cn.yang37.util;

import cn.zhxu.okhttps.HttpResult;
import lombok.extern.slf4j.Slf4j;
import ognl.Ognl;

import java.util.Map;

/**
 * @description:
 * @class: HttpUtils
 * @author: yang37z@qq.com
 * @date: 2024/6/22 22:26
 * @version: 1.0
 */
@Slf4j
public class HttpUtils {

    public static <T> T getValue(String expression, HttpResult.Body body, Class<T> resultType) {
        Map<String, ?> map = GsonUtils.toMap(body.toString());
        return (T) OgnlUtils.wrap(value -> Ognl.getValue(expression, value, resultType)).apply(map);
    }

}
