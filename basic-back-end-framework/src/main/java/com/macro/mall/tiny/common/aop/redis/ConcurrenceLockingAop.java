package com.macro.mall.tiny.common.aop.redis;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 并发控制
 *
 * @author venus
 */
@Component
@Aspect
@Slf4j
@Order(2)
public class ConcurrenceLockingAop {

    /**
     * 存放锁key值的键值
     */
    public static final String LOCKING_KEY_ATTR_KEY = "REDIS_LOCK_LOCKING_KEY";

    /**
     * 切点
     */
    @Pointcut("@annotation(com.macro.mall.tiny.common.aop.redis.RedisLock)")
    public void pointcut() {
    }

    /**
     * 切面逻辑
     *
     * @param joinPoint 连接点
     * @return 返回值
     * @throws Throwable 异常
     */
    @Around("pointcut()")
    public Object locking(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!(joinPoint.getSignature() instanceof MethodSignature)) {
            // 不是方法代理，抛出错误
            throw new Exception("只能并发控制代理的方法");
        }
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Object[] args = joinPoint.getArgs();
        RedisLock redisLock = method.getDeclaredAnnotation(RedisLock.class);
        Object[] keyInfo = this.extractKeyInfo(method, args);
        // 处理 key
        String className = joinPoint.getTarget().getClass().getName();
        String lockingKey = this.getLockingKey((LockingKey) keyInfo[0], keyInfo[1]);
        log.info("执行方法【{}.{}】，开始抢Redis锁，锁的Key：{}", className, method.getName(), lockingKey);
        String key = className + StringPool.DOT + method.getName() + StringPool.COLON + lockingKey;
        // 存储key 以备后续aop会用到
        this.storeLockingKeyInRequest(key);
        // 并发控制
        boolean acquired = RedisLockUtils.lock(key, redisLock.timeout(), redisLock.expiredTime());
        if (!acquired) {
            // 抢占锁失败
            log.info("方法【{}.{}】抢Redis锁失败，锁的Key：{}", className, method.getName(), lockingKey);
            throw new Exception("太挤了，缓缓再试吧");
        }
        // 抢锁成功，执行逻辑
        try {
            log.info("方法【{}.{}】抢Redis锁成功，锁的Key：{}", className, method.getName(), lockingKey);
            return joinPoint.proceed(args);
        } finally {
            // 释放锁
            RedisLockUtils.unlock(key);
        }
    }

    /**
     * 抽取注解信息
     *
     * @param method 连接方法
     * @param args   方法参数
     * @return 注解信息
     */
    private Object[] extractKeyInfo(Method method, Object[] args) {
        return ReflectionOperationUtils.getMethodParameterAnnotation(method, LockingKey.class)
            .map(lockingKeyParameterAnnotation -> new Object[] {lockingKeyParameterAnnotation.getAnnotation(), args[lockingKeyParameterAnnotation.getParameterIndex()]})
            .orElseThrow(() -> new NullPointerException("锁标识不能为空"));
    }

    /**
     * 存储锁键名
     *
     * @param key 键
     */
    private void storeLockingKeyInRequest(String key) {
        if (RequestContextHolder.getRequestAttributes() == null) {
            return;
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        request.setAttribute(LOCKING_KEY_ATTR_KEY, key);
    }

    /**
     * 处理锁键值
     *
     * @param lockingKey 锁键值注解对象
     * @param keyObj     锁目标对象
     * @return 键名
     */
    private String getLockingKey(LockingKey lockingKey, Object keyObj) {
        StringBuilder keyBuilder = new StringBuilder(512);
        String[] fieldNames = lockingKey.fields();
        String[] methodNames = lockingKey.methods();
        final Object finalKeyObj = keyObj;
        if (ArrayUtils.isNotEmpty(fieldNames)) {
            // 处理属性
            keyBuilder.append(
                Arrays.stream(fieldNames)
                    .map(fieldName -> ReflectionOperationUtils.getFieldValue(fieldName, finalKeyObj)
                        .map(FrameworkKeyGenerator::convertToString)
                        .orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining(StringPool.DOT))
            );
        }
        if (ArrayUtils.isNotEmpty(methodNames)) {
            if (keyBuilder.length() > 0) {
                keyBuilder.append(StringPool.DOT);
            }

            keyBuilder.append(
                Arrays.stream(methodNames)
                    .map(methodName -> ReflectionOperationUtils.invokeMethod(methodName, finalKeyObj)
                        .map(FrameworkKeyGenerator::convertToString)
                        .orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining(StringPool.DOT))
            );
        }
        if (keyBuilder.length() == 0) {
            return FrameworkKeyGenerator.convertToString(keyObj);
        }
        return keyBuilder.toString();
    }

}
