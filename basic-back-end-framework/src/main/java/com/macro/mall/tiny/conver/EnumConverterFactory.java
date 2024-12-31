package com.macro.mall.tiny.conver;

import com.google.common.collect.Maps;
import com.macro.mall.tiny.common.enumeration.BaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.Map;

/**
 * 枚举类转换工厂
 *
 * @author ztx
 * @version 1
 */
public class EnumConverterFactory implements ConverterFactory<String, BaseEnum> {
    @SuppressWarnings("rawtypes")
    private static final Map<Class, Converter> CONVERTERS = Maps.newHashMap();

    @Override
    public <T extends BaseEnum> Converter<String, T> getConverter(Class<T> targetType) {
        //noinspection unchecked
        Converter<String, T> converter = CONVERTERS.get(targetType);
        if (converter == null) {
            converter = new EnumConverter(targetType);
            CONVERTERS.put(targetType, converter);
        }
        return converter;
    }
}
