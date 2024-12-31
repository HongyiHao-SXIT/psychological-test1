package com.macro.mall.tiny.common.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 自定义bigdecimal格式化
 *
 * @author ztx
 * @version 1
 */
public class CustomBigDecimalSerialize extends JsonSerializer<BigDecimal> {
    /**
     * Method that can be called to ask implementation to serialize
     * values of type this serializer handles.
     *
     * @param value       Value to serialize; can <b>not</b> be null.
     * @param gen         Generator used to output resulting Json content
     * @param serializers Provider that can be used to get serializers for
     */
    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value != null) {
            value = value.setScale(2, RoundingMode.HALF_UP);
            gen.writeString(value.toString());
        }
    }
}
