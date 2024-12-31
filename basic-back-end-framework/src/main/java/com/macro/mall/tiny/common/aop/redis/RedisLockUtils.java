package com.macro.mall.tiny.common.aop.redis;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.macro.mall.tiny.common.exception.RedisLockingUtilException;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

/**
 * 分布式锁工具类
 *
 * @author venus
 */
@Slf4j
public final class RedisLockUtils {


    /**
     * 值
     */
    private static final String LOCKING_VALUE = "locking";

    /**
     * Redis锁存储键名
     */
    private static final String LOCKING_KEY_PREFIX = "framework" + StringPool.COLON+ "locking" +StringPool.COLON;
    /**
     * 限制只执行一次的Holder键前缀
     */
    private static final String HOLDER_KEY_PREFIX = "framework" + StringPool.COLON + "holder" + StringPool.COLON;

    /**
     * 获取锁
     *
     * @param key         锁标识
     * @param timeout     超时时间
     * @param expiredTime 锁过期时间（防止死锁）
     * @return bool 如果获取锁成功则返回true，否则返回false
     */
    @SuppressWarnings("BusyWait")
    public static boolean lock(String key, int timeout, int expiredTime) {
        timeout = timeout * 1000;
        long currentTime = System.currentTimeMillis();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        while (System.currentTimeMillis() - currentTime <= timeout) {
            if (RedisUtils.setIfAbsent(LOCKING_KEY_PREFIX + key, LOCKING_VALUE, Duration.ofSeconds(expiredTime))) {
                // 获取锁成功
                return true;
            }
            // 获锁不成功
            try {
                Thread.sleep(20, random.nextInt(40));
            } catch (InterruptedException e) {
                log.error("获取不到锁，等待过程中，休眠被中断了：{}", e.getMessage());
                Thread.currentThread().interrupt();
                return false;
            }
        }
        return false;
    }

    /**
     * 取消锁
     *
     * @param key 键
     */
    public static void unlock(String key) {
        RedisUtils.remove(LOCKING_KEY_PREFIX + key);
    }

    /**
     * 带锁调用一个执行器
     *
     * @param key      Key
     * @param executor 执行器
     * @param <R>      泛型
     * @return 直接结果
     */
    public static <R> R executeWithLock(String key, Supplier<R> executor) {
        boolean locking = false;
        try {
            locking = lock(key, 30, 120);
            if (locking) {
                return executor.get();
            }
            throw new RedisLockingUtilException("没有获取到锁【" + key + "】");
        } finally {
            // 解锁
            if (locking) {
                unlock(key);
            }
        }
    }

    /**
     * 两个小时内只能执行一次，一般用于短期大量涌出的事件执行
     *
     * @param holder 标识
     * @param executor 执行器
     * @param <R> 返回值类型
     * @return 执行结果
     */
    public static <R> R executeOnlyOnce(String holder, Supplier<R> executor) {
        String key = HOLDER_KEY_PREFIX + holder;
        boolean success = RedisUtils.setIfAbsent(key, LOCKING_VALUE, Duration.ofMinutes(30));
        if (success) {
            return executor.get();
        }

        return null;
    }

}
