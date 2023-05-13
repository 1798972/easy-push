package cn.yang37.util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @class: GsonUtil
 * @author: yang37z@qq.com
 * @date: 2023/4/12 3:54
 * @version: 1.0
 */
public class GsonUtil {

    private static final Gson GSON;

    private static final Gson GSON_2;

    static {
        GSON = new GsonBuilder().create();
        GSON_2 = new GsonBuilder().setPrettyPrinting().create();
    }


    /**
     * 转成 Json 字符串
     */
    public static String toJson(Object object) {
        return GSON.toJson(object);
    }

    /**
     * 转成 Json 字符串
     */
    public static String toJsonPretty(Object object) {
        return GSON_2.toJson(object);
    }

    /**
     * Json 转 JavaBean 对象
     */
    public static <T> T toBean(String json, Class<T> beanClass) {
        return GSON.fromJson(json, beanClass);
    }

    /**
     * Json 转 List 集合
     */
    public static <T> List<T> toList(String json) {
        Type type = new TypeToken<List<T>>() {
        }.getType();
        return GSON.fromJson(json, type);
    }


    /**
     * Json 转 List 集合
     * 遇到解析不了的，尝试使用这个
     */
    public static <T> List<T> toListExt(String json, Class<T> clz) {
        List<T> mList = new ArrayList<>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        Gson mGson = new Gson();
        for (final JsonElement elem : array) {
            mList.add(mGson.fromJson(elem, clz));
        }
        return mList;
    }


    /**
     * Json 转换成 Map 的 List 集合对象
     *
     * @param json json 字符串
     */
    public static <T> List<Map<String, T>> toListMap(String json) {
        Type type = new TypeToken<List<Map<String, T>>>() {
        }.getType();
        return GSON.fromJson(json, type);
    }

    /**
     * Json 转 Map 对象
     *
     * @param json json 字符串
     */
    public static <T> Map<String, T> toMap(String json) {
        Type type = new TypeToken<Map<String, T>>() {
        }.getType();
        return GSON.fromJson(json, type);
    }
}

