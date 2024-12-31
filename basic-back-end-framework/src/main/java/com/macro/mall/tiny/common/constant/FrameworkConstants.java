package com.macro.mall.tiny.common.constant;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 框架常量定义
 *
 * @author venus
 * @version 1
 * @date 2020/3/25 12:19 下午 星期三
 */
public interface FrameworkConstants {

    /**
     * 日期时间格式化器
     */
    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(FrameworkConstants.DATE_TIME_PATTERN);

    /**
     * 日期段时间格式化器
     */
    DateTimeFormatter DATE_SHORT_TIME_FORMATTER = DateTimeFormatter.ofPattern(FrameworkConstants.DATE_SHORT_TIME_PATTERN);

    /**
     * 日期格式化器
     */
    DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(FrameworkConstants.DATE_PATTERN);

    /**
     * 年月格式化器
     */
    DateTimeFormatter YEAR_MONTH_FORMATTER = DateTimeFormatter.ofPattern(FrameworkConstants.YEAR_MONTH_PATTERN);

    /**
     * 年格式化器
     */
    DateTimeFormatter YEAR_FORMATTER = DateTimeFormatter.ofPattern(FrameworkConstants.YEAR_PATTER);

    /**
     * 时间格式化器
     */
    DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(FrameworkConstants.TIME_PATTERN);

    /**
     * 本地时间构造器
     */
    class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime> {

        /**
         * Gson invokes this call-back method during serialization when it encounters a field of the
         * specified type.
         *
         * <p>In the implementation of this call-back method, you should consider invoking
         * {@link JsonSerializationContext#serialize(Object, Type)} method to create JsonElements for any
         * non-trivial field of the {@code src} object. However, you should never invoke it on the
         * {@code src} object itself since that will cause an infinite loop (Gson will call your
         * call-back method again).</p>
         *
         * @param src       the object that needs to be converted to Json.
         * @param typeOfSrc the actual type (fully genericized version) of the source object.
         * @param context   the context
         * @return a JsonElement corresponding to the specified object.
         */
        @Override
        public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(DATE_TIME_FORMATTER.format(src));
        }
    }

    /**
     * 本地时间构造器
     */
    LocalDateTimeSerializer DATE_TIME_SERIALIZER = new LocalDateTimeSerializer();

    /**
     * 一般JSON操作
     */
    Gson GENERAL_GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
        .enableComplexMapKeySerialization()
        .registerTypeAdapter(LocalDateTime.class, DATE_TIME_SERIALIZER)
        .registerTypeAdapter(Long.class, new JsonDeserializer<Long>() {
            /**
             * Gson invokes this call-back method during deserialization when it encounters a field of the
             * specified type.
             * <p>In the implementation of this call-back method, you should consider invoking
             * {@link JsonDeserializationContext#deserialize(JsonElement, Type)} method to create objects
             * for any non-trivial field of the returned object. However, you should never invoke it on the
             * the same type passing {@code json} since that will cause an infinite loop (Gson will call your
             * call-back method again).
             *
             * @param json    The Json data being deserialized
             * @param typeOfT The type of the Object to deserialize to
             * @param context the
             * @return a deserialized object of the specified type typeOfT which is a subclass of {@code T}
             * @throws JsonParseException if json is not in the expected format of {@code typeofT}
             */
            @Override
            public Long deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                if (!json.isJsonPrimitive()) {
                    return 0L;
                }
                JsonPrimitive jsonPrimitive = json.getAsJsonPrimitive();
                if (jsonPrimitive.isNumber()) {
                    return jsonPrimitive.getAsLong();
                } else if (jsonPrimitive.isBoolean()) {
                    return jsonPrimitive.getAsBoolean() ? 1L : 0;
                } else {
                    try {
                        return Long.valueOf(jsonPrimitive.getAsString());
                    } catch (NumberFormatException e) {
                        return 0L;
                    }
                }
            }
        })
        .registerTypeAdapter(Integer.class, new JsonDeserializer<Integer>() {
            /**
             * Gson invokes this call-back method during deserialization when it encounters a field of the
             * specified type.
             * <p>In the implementation of this call-back method, you should consider invoking
             * {@link JsonDeserializationContext#deserialize(JsonElement, Type)} method to create objects
             * for any non-trivial field of the returned object. However, you should never invoke it on the
             * the same type passing {@code json} since that will cause an infinite loop (Gson will call your
             * call-back method again).
             *
             * @param json    The Json data being deserialized
             * @param typeOfT The type of the Object to deserialize to
             * @param context the
             * @return a deserialized object of the specified type typeOfT which is a subclass of {@code T}
             * @throws JsonParseException if json is not in the expected format of {@code typeofT}
             */
            @Override
            public Integer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                if (!json.isJsonPrimitive()) {
                    return 0;
                }
                JsonPrimitive jsonPrimitive = json.getAsJsonPrimitive();
                if (jsonPrimitive.isNumber()) {
                    return jsonPrimitive.getAsInt();
                } else if (jsonPrimitive.isBoolean()) {
                    return jsonPrimitive.getAsBoolean() ? 1 : 0;
                } else {
                    try {
                        return Integer.valueOf(jsonPrimitive.getAsString());
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                }
            }
        })
        .registerTypeAdapter(Short.class, new JsonDeserializer<Short>() {
            /**
             * Gson invokes this call-back method during deserialization when it encounters a field of the
             * specified type.
             * <p>In the implementation of this call-back method, you should consider invoking
             * {@link JsonDeserializationContext#deserialize(JsonElement, Type)} method to create objects
             * for any non-trivial field of the returned object. However, you should never invoke it on the
             * the same type passing {@code json} since that will cause an infinite loop (Gson will call your
             * call-back method again).
             *
             * @param json    The Json data being deserialized
             * @param typeOfT The type of the Object to deserialize to
             * @param context the
             * @return a deserialized object of the specified type typeOfT which is a subclass of {@code T}
             * @throws JsonParseException if json is not in the expected format of {@code typeofT}
             */
            @Override
            public Short deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                if (!json.isJsonPrimitive()) {
                    return 0;
                }
                JsonPrimitive jsonPrimitive = json.getAsJsonPrimitive();
                if (jsonPrimitive.isNumber()) {
                    return jsonPrimitive.getAsShort();
                } else if (jsonPrimitive.isBoolean()) {
                    return jsonPrimitive.getAsBoolean() ? (short) 1 : 0;
                } else {
                    try {
                        return Short.valueOf(jsonPrimitive.getAsString());
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                }
            }
        })
        .setLenient()
        .disableHtmlEscaping()
        .create();

    /**
     * 下划线转驼峰JSON
     */
    Gson UNDERSCORE_GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
        .enableComplexMapKeySerialization()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .registerTypeAdapter(LocalDateTime.class, DATE_TIME_SERIALIZER)
        .setLenient()
        .disableHtmlEscaping()
        .create();

    /**
     * 美化JSON
     */
    Gson PRETTY_GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
        .enableComplexMapKeySerialization()
        .setPrettyPrinting()
        .registerTypeAdapter(LocalDateTime.class, DATE_TIME_SERIALIZER)
        .setLenient()
        .disableHtmlEscaping()
        .create();

    /**
     * 日期时间格式
     */
    String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期段时间格式
     */
    String DATE_SHORT_TIME_PATTERN = "yyyy-MM-dd HH:mm";

    /**
     * 日期格式
     */
    String DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 数字日期格式
     */
    String DATE_PATTERN_NUMBER = "yyyyMMdd";

    /**
     * 年月格式
     */
    String YEAR_MONTH_PATTERN = "yyyy-MM";

    /**
     * 年格式
     */
    String YEAR_PATTER = "yyyy";

    /**
     * 时间格式
     */
    String TIME_PATTERN = "HH:mm:ss";

    /**
     * 数字时间格式
     */
    String TIME_PATTERN_NUMBER = "HHmmss";


}
