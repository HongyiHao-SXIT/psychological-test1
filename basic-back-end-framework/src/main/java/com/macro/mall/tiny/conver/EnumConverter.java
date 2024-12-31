package com.macro.mall.tiny.conver;

import com.google.common.collect.Maps;
import com.macro.mall.tiny.common.enumeration.BaseEnum;
import org.springframework.core.convert.converter.Converter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

/**
 * 枚举类转换器
 *
 * @author ztx
 * @version 1
 */
public class EnumConverter<T extends BaseEnum> implements Converter<String, T> {
    private final Map<String, T> enumMap = Maps.newHashMap();

    public EnumConverter(Class<T> enumType) {
        Arrays.stream(enumType.getEnumConstants())
            .forEach(x -> {
                enumMap.put(x.getValue().toString(), x);
            });
    }

    @Override
    public T convert(String source) {
        return Optional.of(source)
            .map(enumMap::get)
            .orElseGet(() -> Optional.of(source)
                .map(enumMap::get)
                .orElseThrow(() -> new NullPointerException("转换类异常")));
    }
}
