package com.macro.mall.tiny.common.aop.redis;

import cn.hutool.core.convert.Convert;
import org.springframework.stereotype.Component;

/**
 * 一般查询Key生成器
 *
 * @author venus
 * @version 1
 * @date 2020/3/26 1:47 下午 星期四
 */
@Component
public class FrameworkKeyGenerator {
    /**
     * 将一个对象转为字符串形式
     *
     * @param object 对象
     * @return 字符串
     */
    public static String convertToString(Object object) {
        return Convert.toStr(object);
    }
}
