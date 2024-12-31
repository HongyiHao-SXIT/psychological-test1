package com.macro.mall.tiny.common.aop.redis;

/**
 * 类型转换工具类
 *
 * @author venus
 * @version 1
 * @date 2020/4/1 4:46 下午 星期三
 */
public final class TypeCastUtils {

    /**
     * 类型转换
     *
     * @param object 对象
     * @param <T>    目标类型泛型
     * @return 目标对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object object) {
        return (T) object;
    }
}
