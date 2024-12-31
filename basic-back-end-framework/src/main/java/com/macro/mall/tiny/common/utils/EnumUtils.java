package com.macro.mall.tiny.common.utils;

import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * 枚举操作类
 *
 * @author ztx
 * @version 1
 */
public class EnumUtils {
    /**
     * 值映射为枚举
     *
     * @param enumClass 枚举类
     * @param value     枚举值
     * @param method    取值方法
     * @param <E>       对应枚举
     * @return 枚举类
     */
    public static <E extends Enum<?>> E valueOf(Class<E> enumClass, Object value, Method method) {
        E[] es = enumClass.getEnumConstants();
        for (E e : es) {
            Object evalue;
            try {
                method.setAccessible(true);
                evalue = method.invoke(e);
            } catch (IllegalAccessException | InvocationTargetException e1) {
                throw ExceptionUtils.mpe("Error: NoSuchMethod in {}.  Cause:", e, enumClass.getName());
            }
            if (value instanceof Number && evalue instanceof Number
                && new BigDecimal(String.valueOf(value)).compareTo(new BigDecimal(String.valueOf(evalue))) == 0) {
                return e;
            }
            if (Objects.equals(evalue, value)) {
                return e;
            }
        }
        return null;
    }

    /**
     * 根据value值获取enum对象
     *
     * @param enumClass 枚举类
     * @param value     value
     * @param <E>       泛型
     * @return 对象
     */
    public static <E extends Enum<E>> E getEnumByValue(final Class<E> enumClass, Object value) {
        try {
            return valueOf(enumClass, value, enumClass.getMethod("getValue"));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据value值获取name
     *
     * @param enumClass 枚举没
     * @param value     value
     * @param <E>       泛型
     * @return name
     */
    public static <E extends Enum<E>> String getTextByValue(final Class<E> enumClass, Object value) {
        E e = getEnumByValue(enumClass, value);
        Object evalue;
        Method method = null;
        try {
            method = enumClass.getMethod("getName");
            method.setAccessible(true);
            evalue = method.invoke(e);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e1) {
            throw ExceptionUtils.mpe("Error: NoSuchMethod in {}.  Cause:", e, enumClass.getName());
        }
        if (evalue != null) {
            return String.valueOf(evalue);
        }
        return null;
    }
}
