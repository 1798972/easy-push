package cn.yang37.util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * GsonUtils 提供了对 JSON 数据的序列化和反序列化操作，以及自定义功能支持。
 *
 * @class: GsonUtil
 * @auther: yang37z@qq.com
 * @date: 2023/4/12 3:54
 * @version: 1.0
 */
public class GsonUtils {

    private static final Gson GSON;

    private static final Gson GSON_2;

    private static final Gson GSON_3;

    private static final Gson GSON_4;

    static {
        GSON = new GsonBuilder().create();

        GSON_2 = new GsonBuilder().setPrettyPrinting().create();

        GSON_3 = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .registerTypeAdapter(Map.class, new MapSerializer())
                .create();

        GSON_4 = new GsonBuilder().
                setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

    /**
     * 自定义Map序列化器，保证字段按字母顺序排序
     */
    private static class MapSerializer implements JsonSerializer<Map<?, ?>> {
        @Override
        public JsonElement serialize(Map<?, ?> src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            src.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey(Comparator.comparing(Object::toString)))
                    .forEach(entry -> jsonObject.add(String.valueOf(entry.getKey()), context.serialize(entry.getValue())));
            return jsonObject;
        }
    }

    /**
     * object -> json
     *
     * @param object 要序列化为 JSON 的对象
     * @return JSON 字符串
     */
    public static String toJson(Object object) {
        return GSON.toJson(object);
    }

    /**
     * object -> json/大写驼峰
     *
     * @param object 要序列化为 JSON 的对象
     * @return JSON 字符串
     */
    public static String toJsonUpperCamelCase(Object object) {
        return GSON_3.toJson(object);
    }

    /**
     * object -> json/下划线
     *
     * @param object 要序列化为 JSON 的对象
     * @return 使用下划线分隔的 JSON 字符串
     */
    public static String toJsonSnakeCase(Object object) {
        return GSON_4.toJson(object);
    }

    /**
     * object -> json/pretty
     *
     * @param object 要序列化为 JSON 的对象
     * @return 格式化后的 JSON 字符串
     */
    public static String toJsonPretty(Object object) {
        return GSON_2.toJson(object);
    }

    /**
     * json -> bean
     *
     * @param json      JSON 字符串
     * @param beanClass JavaBean 的 Class 类型
     * @param <T>       JavaBean 的类型
     * @return 转换后的 JavaBean 对象
     */
    public static <T> T toBean(String json, Class<T> beanClass) {
        return GSON.fromJson(json, beanClass);
    }

    /**
     * json -> bean/大写驼峰
     *
     * @param json      JSON 字符串
     * @param beanClass JavaBean 的 Class 类型
     * @param <T>       JavaBean 的类型
     * @return 转换后的 JavaBean 对象
     */
    public static <T> T toBeanUpperCamelCase(String json, Class<T> beanClass) {
        return GSON_3.fromJson(json, beanClass);
    }

    /**
     * 将 JSON 字符串转换为 List 集合。
     *
     * @param json JSON 字符串
     * @param <T>  List 中元素的类型
     * @return 转换后的 List 集合
     */
    public static <T> List<T> toList(String json) {
        Type type = new TypeToken<List<T>>() {
        }.getType();
        return GSON.fromJson(json, type);
    }


    /**
     * 将 JSON 字符串转换为 List 集合，处理解析不了的情况。
     *
     * @param json JSON 字符串
     * @param clz  List 中元素的 Class 类型
     * @param <T>  List 中元素的类型
     * @return 转换后的 List 集合
     */
    public static <T> List<T> toListExt(String json, Class<T> clz) {
        List<T> mList = new ArrayList<>();
        JsonArray array = JsonParser.parseString(json).getAsJsonArray();
        Gson mGson = new Gson();
        for (final JsonElement elem : array) {
            mList.add(mGson.fromJson(elem, clz));
        }
        return mList;
    }


    /**
     * 将 JSON 字符串转换为 Map 的 List 集合对象。
     *
     * @param json JSON 字符串
     * @param <T>  Map 中 value 的类型
     * @return 转换后的 Map 的 List 集合对象
     */
    public static <T> List<Map<String, T>> toListMap(String json) {
        Type type = new TypeToken<List<Map<String, T>>>() {
        }.getType();
        return GSON.fromJson(json, type);
    }

    /**
     * 将 JSON 字符串转换为 Map 对象。
     *
     * @param json JSON 字符串
     * @param <T>  Map 中 value 的类型
     * @return 转换后的 Map 对象
     */
    public static <T> Map<String, T> toMap(String json) {
        Type type = new TypeToken<Map<String, T>>() {
        }.getType();
        return GSON.fromJson(json, type);
    }

}
