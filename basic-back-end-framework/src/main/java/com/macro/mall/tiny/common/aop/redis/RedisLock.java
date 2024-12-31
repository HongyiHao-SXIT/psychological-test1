package com.macro.mall.tiny.common.aop.redis;

import java.lang.annotation.*;

/**
 * 分布式锁
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisLock {

    /**
     * 五秒轮询等待 单位秒
     *
     * @return 超时时间
     */
    int timeout() default 60;

    /**
     * 120秒超时 单位秒
     *
     * @return 锁过期时间
     */
    int expiredTime() default 120;

}
