package com.bootx.mall.auth.util;

import com.fasterxml.jackson.databind.JavaType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import org.springframework.util.Assert;

/**
 * GJson
 *
 * @author 夏黎
 * @version 1.0
 * @date 2020-07-04 11:31:19
 */
public final class GJsonUtils {

    /**
     * ObjectMapper
     */
    private static final Gson gson = new GsonBuilder().serializeNulls().create();

    /**
     * 不可实例化
     */
    private GJsonUtils() {
    }

    /**
     * 将对象转换为JSON字符串
     *
     * @param value
     *            对象
     * @return JSON字符串
     */
    public static String toJson(Object value) {
        Assert.notNull(value, "[Assertion failed] - value is required; it must not be null");
        return gson.toJson(value);
    }

    /**
     * 将JSON字符串转换为对象
     *
     * @param json
     *            JSON字符串
     * @param valueType
     *            类型
     * @return 对象
     */
    public static <T> T toObject(String json, Class<T> valueType) {
        Assert.hasText(json, "[Assertion failed] - json must have text; it must not be null, empty, or blank");
        Assert.notNull(valueType, "[Assertion failed] - valueType is required; it must not be null");
        return gson.fromJson(json, valueType);
    }

    /**
     * 将JSON字符串转换为对象
     *
     * @param json
     *            JSON字符串
     * @param javaType
     *            类型
     * @return 对象
     */
    public static <T> T toObject(String json, JavaType javaType) {
        Assert.hasText(json, "[Assertion failed] - json must have text; it must not be null, empty, or blank");
        Assert.notNull(javaType, "[Assertion failed] - javaType is required; it must not be null");
        return gson.fromJson(json,javaType);
    }

    /**
     * 将JSON字符串转换为树
     *
     * @param json
     *            JSON字符串
     * @return 树
     */
    public static JsonElement toTree(String json) {
        Assert.hasText(json, "[Assertion failed] - json must have text; it must not be null, empty, or blank");
        return gson.toJsonTree(json);
    }
}
