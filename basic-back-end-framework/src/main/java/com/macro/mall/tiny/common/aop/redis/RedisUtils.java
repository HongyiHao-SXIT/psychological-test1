package com.macro.mall.tiny.common.aop.redis;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.*;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Redis工具类
 *
 * @author venus
 */
@Component
@SuppressWarnings("unchecked")
public final class RedisUtils {

    private static RedisTemplate<String, Object> redisTemplate;
    private static RedisTemplate<String, String> stringRedisTemplate;

    @Autowired
    public RedisUtils(RedisTemplate<String, Object> redisTemplate, @Qualifier("stringRedisTemplate") RedisTemplate<String, String> stringRedisTemplate) {
        RedisUtils.redisTemplate = redisTemplate;
        RedisUtils.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 设置一个值 不限时长
     *
     * @param key   键
     * @param value 值
     */
    public static void set(String key, Object value) {
        set(key, value, null);
    }

    /**
     * 设置一个值
     *
     * @param key      键
     * @param value    值
     * @param duration 存活时间
     */
    public static void set(String key, Object value, Duration duration) {
        set(key, value, duration, redisTemplate);
    }

    /**
     * 设置一个值
     *
     * @param key             键
     * @param value           值
     * @param duration        存活时间
     * @param <V>             值类型
     * @param redisOperations REDIS操作对象
     */
    public static <V> void set(String key, V value, Duration duration, RedisOperations<String, V> redisOperations) {
        if (Objects.isNull(duration) || duration.isZero()) {
            // 不限时长
            redisOperations.opsForValue().set(key, value);
            return;
        }

        redisOperations.opsForValue().set(key, value, duration);
    }

    /**
     * 设置一个字符串值
     *
     * @param key      键
     * @param value    值
     * @param duration 存活时间
     */
    public static void setString(String key, String value, Duration duration) {
        set(key, value, duration, stringRedisTemplate);
    }

    /**
     * 获取一个字符串值
     *
     * @param key 键
     * @return 值
     */
    public static String getString(String key) {
        return get(key, stringRedisTemplate);
    }

    /**
     * 获取一个字符串值
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 值
     */
    public static String getString(String key, String defaultValue) {
        String value = getString(key);
        if (StringUtils.isNotBlank(value)) {
            return value;
        }

        return defaultValue;
    }

    /**
     * 对一个值进行自增
     *
     * @param key   键
     * @param delta 增量
     * @return 增长前的值
     */
    public static Long increment(String key, long delta) {
        return stringRedisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 获取一个值
     *
     * @param key 键
     * @param <T> 泛型
     * @return 值
     */
    public static <T> T get(String key) {
        return get(key, redisTemplate);
    }

    /**
     * 获取一个值
     *
     * @param key             键
     * @param <T>             泛型
     * @param <V>             值类型
     * @param redisOperations REDIS操作对象
     * @return 值
     */
    public static <T, V> T get(String key, RedisOperations<String, V> redisOperations) {
        V value = redisOperations.opsForValue().get(key);
        if (Objects.isNull(value)) {
            return null;
        }

        return (T) value;
    }

    /**
     * 获取多个Redis值
     *
     * @param keyPrefix key前缀
     * @param <T>       泛型
     * @return 值列表
     */
    public static <T> List<T> getAll(String keyPrefix) {
        Set<String> keys = redisTemplate.keys(keyPrefix + "*");
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptyList();
        }
        List<Object> result = redisTemplate.opsForValue().multiGet(keys);
        if (CollectionUtils.isEmpty(result)) {
            return Collections.emptyList();
        }
        return TypeCastUtils.cast(result);
    }

    /**
     * 获取一个值
     *
     * @param key      键
     * @param supplier 默认值
     * @param <T>      泛型
     * @return 值
     */
    public static <T> T getIfAbsent(String key, Supplier<T> supplier) {
        Object value = get(key, redisTemplate);
        if (Objects.isNull(value)) {
            return supplier.get();
        }

        return (T) value;
    }

    /**
     * 设置一个值通过回调，如果不存在的话
     *
     * @param key      键值
     * @param supplier 值提供器
     * @param duration 存活时长
     * @param <T>      泛型
     */
    public static <T> void setIfAbsent(String key, Supplier<T> supplier, Duration duration) {
        if (!hasKey(key)) {
            set(key, supplier.get(), duration);
        }
    }

    /**
     * 设置一个值通过回调，如果不存在的话
     *
     * @param key      键值
     * @param supplier 值提供器
     * @param <T>      泛型
     */
    public static <T> void setIfAbsent(String key, Supplier<T> supplier) {
        setIfAbsent(key, supplier, Duration.ZERO);
    }

    /**
     * 设置Key的过期时间
     *
     * @param key      key
     * @param duration 持续时间
     */
    public static void expire(String key, Duration duration) {
        if (duration == null || duration.isZero()) {
            remove(key);
            return;
        }

        redisTemplate.expire(key, duration);
    }

    /**
     * 删除一个键
     *
     * @param key 键
     */
    public static void remove(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 批量删除Key
     *
     * @param keyPrefix key前缀
     */
    public static void removeAll(String keyPrefix) {
        removeAll(keyPrefix, redisTemplate);
    }

    /**
     * 批量删除Key
     *
     * @param keyPrefix       key前缀
     * @param redisOperations REDIS操作对象
     */
    public static void removeAll(String keyPrefix, RedisOperations<String, ?> redisOperations) {
        Set<String> keys = redisOperations.keys(keyPrefix + "*");
        if (!CollectionUtils.isEmpty(keys)) {
            redisOperations.delete(keys);
        }
    }

    /**
     * 如果键不存在，则设置它
     *
     * @param key      键
     * @param value    值
     * @param duration 存活时间
     * @return 设置成功返回true
     */
    public static boolean setIfAbsent(String key, String value, Duration duration) {
        return BooleanUtils.isTrue(stringRedisTemplate.opsForValue().setIfAbsent(key, value, duration));
    }

    /**
     * 获取HashMap操作
     *
     * @param key  键
     * @param <HK> hash键
     * @param <HV> hash值
     * @return 操作对象
     */
    public static <HK, HV> BoundHashOperations<String, HK, HV> getBoundHashOperations(String key) {
        return redisTemplate.boundHashOps(key);
    }

    /**
     * 获取List操作
     *
     * @return 操作对象
     */
    public static ListOperations<String, Object> getListOperations() {
        return redisTemplate.opsForList();
    }

    /**
     * 获取key剩余时间
     *
     * @param key 键名
     * @return 剩余时间（秒）
     */
    public static long getKeyExpiredTime(String key) {
        Long seconds = redisTemplate.getExpire(key);
        if (Objects.isNull(seconds)) {
            return 0;
        }
        return seconds;
    }

    /**
     * 判断Redis中是否有某一个Key
     *
     * @param key 键名
     * @return bool
     */
    public static boolean hasKey(String key) {
        return BooleanUtils.isTrue(redisTemplate.hasKey(key));
    }

    /**
     * 批量执行Redis命令
     *
     * @param executor 执行器
     */
    public static void executeBatch(Consumer<RedisOperations<String, Object>> executor) {
        redisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            public Object execute(@NonNull RedisOperations operations) throws DataAccessException {
                executor.accept(operations);
                return null;
            }
        }, redisTemplate.getKeySerializer());
    }

}
