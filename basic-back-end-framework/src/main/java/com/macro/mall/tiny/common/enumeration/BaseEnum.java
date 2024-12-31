package com.macro.mall.tiny.common.enumeration;

/**
 * 基类枚举类
 *
 * @author ztx
 * @version 1
 */
public interface BaseEnum<E extends Enum<?>, T> {

    /**
     * 获取枚举值
     * @return 枚举值
     */
    T getValue();

    /**
     * 获取枚举值描述
     * @return 枚举值的描述
     */
    String getName();

}