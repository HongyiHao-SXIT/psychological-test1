package com.macro.mall.tiny.common.aop.redis;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.google.common.collect.Lists;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 反射操作工具类
 *
 * @author venus
 * @version 1
 * @date 2020/3/23 12:32 下午 星期一
 */
@Slf4j
public final class ReflectionOperationUtils {

    /**
     * 缓存类字段
     */
    private static final Map<Class<?>, List<Field>> CLASS_FIELDS_CACHE = new ConcurrentReferenceHashMap<>(256);

    /**
     * 缓存类字段map对象
     */
    private static final Map<Class<?>, Map<String, Field>> CLASS_FIELD_MAP_CACHE = new ConcurrentReferenceHashMap<>(256);

    /**
     * 缓存类中指定注解的字段
     */
    private static final Map<Class<?>, Map<Class<? extends Annotation>, List<Field>>> CLASS_ANNOTATED_FIELDS_CACHE = new ConcurrentReferenceHashMap<>(256);

    /**
     * 缓存类中指定注解的方法
     */
    private static final Map<Class<?>, Map<Class<? extends Annotation>, List<Method>>> CLASS_ANNOTATED_METHODS_CACHE = new ConcurrentReferenceHashMap<>(256);

    /**
     * 缓存类中的命名字段
     */
    private static final Map<Class<?>, Map<String, Field>> CLASS_NAME_FIELDS_MAP = new ConcurrentReferenceHashMap<>(256);

    /**
     * 缓存类中的命名方法
     */
    private static final Map<Class<?>, Map<MethodCacheKey, Method>> CLASS_NAME_METHODS_MAP = new ConcurrentReferenceHashMap<>(256);

    /**
     * 缓存类中的默认构造方法
     */
    private static final Map<Class<?>, Constructor<?>> CLASS_CONSTRUCTOR_MAP = new ConcurrentReferenceHashMap<>(256);

    /**
     * 缓存方法参数注解信息
     */
    private static final Map<Method, ParameterAnnotation<?>> METHOD_PARAMETER_ANNOTATION_OPTIONAL_MAP = new ConcurrentReferenceHashMap<>(128);

    /**
     * get 方法前缀
     */
    public static final String GETTER_METHOD_PREFIX = "get";
    /**
     * set 方法前缀
     */
    public static final String SETTER_METHOD_PREFIX = "set";

    /**
     * 清空反射缓存
     *
     * @param clazz 重载的类
     */
    public static void clearCache(Class<?> clazz) {
        if (CLASS_FIELDS_CACHE.remove(clazz) != null) {
            log.info("检测到类【{}】发生变化，清除其反射字段缓存", clazz.getName());
        }
        if (CLASS_FIELD_MAP_CACHE.remove(clazz) != null) {
            log.info("检测到类【{}】发生变化，清除其反射字段Map缓存", clazz.getName());
        }
        if (CLASS_ANNOTATED_FIELDS_CACHE.remove(clazz) != null) {
            log.info("检测到类【{}】发生变化，清除其反射注解字段Map缓存", clazz.getName());
        }
        if (CLASS_ANNOTATED_METHODS_CACHE.remove(clazz) != null) {
            log.info("检测到类【{}】发生变化，清除其反射注解方法Map缓存", clazz.getName());
        }
        if (CLASS_NAME_FIELDS_MAP.remove(clazz) != null) {
            log.info("检测到类【{}】发生变化，清除其反射名称字段Map缓存", clazz.getName());
        }
        if (CLASS_NAME_METHODS_MAP.remove(clazz) != null) {
            log.info("检测到类【{}】发生变化，清除其反射名称方法Map缓存", clazz.getName());
        }
        if (CLASS_CONSTRUCTOR_MAP.remove(clazz) != null) {
            log.info("检测到类【{}】发生变化，清除其反射构造函数缓存", clazz.getName());
        }
        for (Method method : ReflectionUtils.getAllDeclaredMethods(clazz)) {
            if (METHOD_PARAMETER_ANNOTATION_OPTIONAL_MAP.remove(method) != null) {
                log.info("检测到类【{}】发生变化，清除其方法【{}】反射参数注解缓存", clazz.getName(), method.getName());
            }
        }
    }

    /**
     * 获取类字段根据名称
     *
     * @param clazz     类
     * @param fieldName 字段名称
     * @return 字段对象
     */
    public static Field getField(Class<?> clazz, String fieldName) {
        Class<?> userClass = ClassUtils.getUserClass(clazz);
        Map<String, Field> fieldsMap = CLASS_NAME_FIELDS_MAP.computeIfAbsent(userClass, key -> (Map<String, Field>) new ConcurrentReferenceHashMap<String, Field>(64));
        return fieldsMap.computeIfAbsent(fieldName, key -> {
            Field field = ReflectionUtils.findField(userClass, key);
            if (Objects.nonNull(field)) {
                ReflectionUtils.makeAccessible(field);
                fieldsMap.put(key, field);
            }
            return field;
        });
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @AllArgsConstructor(staticName = "of")
    private static class MethodCacheKey {
        /**
         * 方法名称
         */
        private String methodName;
        /**
         * 方法参数类型
         */
        private Class<?>[] argumentTypes;
    }

    /**
     * 获取类方法根据名称
     *
     * @param clazz         类
     * @param methodName    方法名称
     * @param argumentTypes 参数类型
     * @return 方法对象
     */
    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... argumentTypes) {
        Class<?> userClass = ClassUtils.getUserClass(clazz);
        Map<MethodCacheKey, Method> methodsMap = CLASS_NAME_METHODS_MAP.computeIfAbsent(userClass, key -> new ConcurrentReferenceHashMap<>(64));
        return methodsMap.computeIfAbsent(MethodCacheKey.of(methodName, argumentTypes), key -> {
            Method method = ReflectionUtils.findMethod(userClass, methodName, argumentTypes);
            if (Objects.nonNull(method)) {
                ReflectionUtils.makeAccessible(method);
                methodsMap.put(key, method);
            }
            return method;
        });
    }

    /**
     * 设置字段值
     *
     * @param field  字段对象
     * @param target 目标对象
     * @param value  值
     */
    public static void setFieldValue(Field field, Object target, Object value) {
        ReflectionUtils.makeAccessible(field);
        ReflectionUtils.setField(field, target, value);
    }

    /**
     * 设置字段值 通过set方法
     *
     * @param fieldName 字段名称
     * @param target    目标对象
     * @param value     值
     */
    public static void setFieldValue(String fieldName, Object target, Object value) {
        setFieldValue(target.getClass(), fieldName, target, value);
    }

    /**
     * 设置静态字段值
     *
     * @param field 字段对象
     * @param value 值
     */
    public static void setStaticFieldValue(Field field, Object value) {
        ReflectionUtils.makeAccessible(field);
        ReflectionUtils.setField(field, null, value);
    }

    /**
     * 设置静态字段值
     *
     * @param clazz     类
     * @param fieldName 字段名称
     * @param value     值
     */
    public static void setStaticFieldValue(Class<?> clazz, String fieldName, Object value) {
        Class<?> userClass = ClassUtils.getUserClass(clazz);
        Field field = getField(userClass, fieldName);
        if (Objects.isNull(field)) {
            log.warn("类{}中找不到{}属性", userClass.getName(), fieldName);
            return;
        }
        setStaticFieldValue(field, value);
    }

    /**
     * 设置字段值 通过set方法
     *
     * @param clazz     类
     * @param fieldName 字段名称
     * @param target    目标对象
     * @param value     值
     */
    public static void setFieldValue(Class<?> clazz, String fieldName, Object target, Object value) {
        Class<?> userClass = ClassUtils.getUserClass(clazz);
        Field field = getField(userClass, fieldName);
        if (Objects.isNull(field)) {
            log.warn("类{}中找不到{}属性", userClass.getName(), fieldName);
            return;
        }
        setFieldValue(userClass, field, target, value);
    }

    /**
     * 设置字段值 通过set方法
     *
     * @param clazz  类
     * @param field  字段对象
     * @param target 目标对象
     * @param value  值
     */
    public static void setFieldValue(Class<?> clazz, Field field, Object target, Object value) {
        Method method = getMethod(clazz, SETTER_METHOD_PREFIX + StringUtils.capitalize(field.getName()));
        if (Objects.nonNull(method)) {
            // 调用set方法
            ReflectionUtils.invokeMethod(method, target, value);
            return;
        }
        // 没有set方法，则直接赋值
        setFieldValue(field, target, value);
    }

    /**
     * 获取字段值
     *
     * @param field  字段对象
     * @param target 目标对象
     * @return optional结果
     */
    public static Optional<Object> getFieldValue(Field field, Object target) {
        ReflectionUtils.makeAccessible(field);
        return Optional.ofNullable(ReflectionUtils.getField(field, target));
    }

    /**
     * 获取字段值
     *
     * @param clazz  类
     * @param field  字段对象
     * @param target 目标对象
     * @return optional结果
     */
    public static Optional<Object> getFieldValue(Class<?> clazz, Field field, Object target) {
        Method method = getMethod(clazz, GETTER_METHOD_PREFIX + StringUtils.capitalize(field.getName()));
        if (method != null) {
            // 调用get方法
            return Optional.ofNullable(ReflectionUtils.invokeMethod(method, target));
        }
        // 没有get方法，则直接取值
        return getFieldValue(field, target);
    }

    /**
     * 获取字段值
     *
     * @param fieldName 字段对象
     * @param target    目标对象
     * @return optional结果
     */
    public static Optional<Object> getFieldValue(String fieldName, Object target) {
        if (Objects.isNull(target)) {
            return Optional.empty();
        }
        return getFieldValue(target.getClass(), fieldName, target);
    }

    /**
     * 获取字段值
     *
     * @param clazz     类
     * @param fieldName 字段对象
     * @param target    目标对象
     * @return optional结果
     */
    public static Optional<Object> getFieldValue(Class<?> clazz, String fieldName, Object target) {
        Field field = getField(clazz, fieldName);
        if (Objects.isNull(field)) {
            log.warn("类{}中找不到{}属性", clazz.getName(), fieldName);
            return Optional.empty();
        }
        return getFieldValue(clazz, field, target);
    }

    /**
     * 调用方法
     *
     * @param method    方法对象
     * @param target    目标对象
     * @param arguments 调用传参
     * @return optional结果
     */
    public static Optional<Object> invokeMethod(Method method, Object target, Object... arguments) {
        ReflectionUtils.makeAccessible(method);
        return Optional.ofNullable(ReflectionUtils.invokeMethod(method, target, arguments));
    }

    /**
     * 调用方法
     *
     * @param clazz      类
     * @param methodName 方法名称
     * @param target     目标对象
     * @param arguments  调用传参
     * @return optional结果
     */
    public static Optional<Object> invokeMethod(Class<?> clazz, String methodName, Object target, Object... arguments) {
        Method method;
        if (arguments == null || arguments.length == 0) {
            method = getMethod(clazz, methodName);
        } else {
            method = getMethod(clazz, methodName, Arrays.stream(arguments).map(Object::getClass).toArray(Class[]::new));
        }
        if (Objects.isNull(method)) {
            log.warn("类{}中找不到{}方法", clazz.getName(), methodName);
            return Optional.empty();
        }
        return invokeMethod(method, target, arguments);
    }

    /**
     * 调用方法
     *
     * @param methodName 方法名称
     * @param target     目标对象
     * @param arguments  调用传参
     * @return optional结果
     */
    public static Optional<Object> invokeMethod(String methodName, Object target, Object... arguments) {
        return invokeMethod(ClassUtils.getUserClass(target), methodName, target, arguments);
    }

    /**
     * 调用方法
     *
     * @param clazz               类
     * @param methodName          方法名称
     * @param methodArgumentTypes 方法参数类型
     * @param target              目标对象
     * @param arguments           调用传参
     * @return optional结果
     */
    public static Optional<Object> invokeMethodPrecisely(Class<?> clazz, String methodName, List<Class<?>> methodArgumentTypes, Object target, Object... arguments) {
        Method method = ReflectionUtils.findMethod(clazz, methodName, methodArgumentTypes.toArray(new Class<?>[0]));
        if (Objects.isNull(method)) {
            log.warn("类{}中找不到{}方法", clazz.getName(), methodName);
            return Optional.empty();
        }
        return invokeMethod(method, target, arguments);
    }

    /**
     * 调用方法
     *
     * @param clazz      类
     * @param methodName 方法名称
     * @param target     目标对象
     * @param arguments  调用传参
     * @return optional结果
     */
    public static Optional<Object> invokeMethodPrecisely(Class<?> clazz, String methodName, Object target, Object... arguments) {
        Method method = ReflectionUtils.findMethod(clazz, methodName,
            Arrays.stream(arguments)
                .map(Object::getClass)
                .toArray(Class<?>[]::new)
        );
        if (Objects.isNull(method)) {
            log.warn("类{}中找不到{}方法", clazz.getName(), methodName);
            return Optional.empty();
        }
        return invokeMethod(method, target, arguments);
    }

    /**
     * 实例化一个对象
     *
     * @param clazz 类
     * @param <T>   泛型
     * @return 实例
     */
    public static <T> Optional<T> newInstance(Class<T> clazz) {
        // 从缓存拿出构造函数对象
        Constructor<T> ctor = TypeCastUtils.cast(
            CLASS_CONSTRUCTOR_MAP.computeIfAbsent(
                clazz,
                key -> {
                    Constructor<T> constructor = ClassUtils.getConstructorIfAvailable(clazz);
                    if (Objects.isNull(constructor)) {
                        return null;
                    }
                    ReflectionUtils.makeAccessible(constructor);
                    return constructor;
                }
            )
        );

        if (ctor == null) {
            return Optional.empty();
        }

        try {
            return Optional.of(ctor.newInstance());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            log.error("类{}的构造函数{}实例化失败：", ClassUtils.getShortName(clazz), ctor.getName(), e);
            return Optional.empty();
        }
    }

    /**
     * 获取过滤后的类字段（包括继承）
     *
     * @param clazz  类
     * @param filter 过滤器
     * @return 字段列表
     */
    public static List<Field> getClassAllDeclaredFieldsWithoutCache(Class<?> clazz, Predicate<Field> filter) {
        Class<?> currentClass = clazz;
        List<Field> fields = Lists.newArrayListWithCapacity(32);
        while (Objects.nonNull(currentClass) && !ClassOperationUtils.isObjectClass(currentClass)) {
            fields.addAll(Arrays.asList(currentClass.getDeclaredFields()));
            currentClass = currentClass.getSuperclass();
        }
        fields.removeIf(field -> !filter.test(field));
        return fields;
    }

    /**
     * 获取类所有的字段，包括继承的
     *
     * @param clazz 类
     * @return 字段列表
     */
    public static List<Field> getClassAllDeclaredFields(Class<?> clazz) {
        // 如果有缓存则直接走缓存
        return CLASS_FIELDS_CACHE.computeIfAbsent(
            clazz,
            key -> {
                // 去除静态字段
                List<Field> fields = getClassAllDeclaredFieldsWithoutCache(key, field -> !Modifier.isStatic(field.getModifiers()));
                // 加入缓存
                List<Field> newFields = Lists.newArrayListWithCapacity(fields.size());
                newFields.addAll(fields);

                return newFields;
            }
        );
    }

    /**
     * 获取类字段map对象
     *
     * @param clazz 类
     * @return 字段map
     */
    public static Map<String, Field> getClassAllDeclaredFieldMap(Class<?> clazz) {
        return CLASS_FIELD_MAP_CACHE.computeIfAbsent(clazz, key -> getClassAllDeclaredFields(key).stream()
            .collect(Collectors.toMap(Field::getName, field -> field)));
    }

    /**
     * 获取类中所有标有指定注解的字段
     *
     * @param clazz           类
     * @param annotationClass 注解类
     * @return 字段列表
     */
    public static List<Field> getAnnotatedFields(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        if (CLASS_ANNOTATED_FIELDS_CACHE.containsKey(clazz)) {
            Map<Class<? extends Annotation>, List<Field>> map = CLASS_ANNOTATED_FIELDS_CACHE.get(clazz);
            if (map.containsKey(annotationClass)) {
                return map.get(annotationClass);
            }
        } else {
            CLASS_ANNOTATED_FIELDS_CACHE.put(clazz, new ConcurrentReferenceHashMap<>(64));
        }
        List<Field> fields = getClassAllDeclaredFields(clazz);
        List<Field> annotatedFields = fields.stream()
            .filter(field -> field.isAnnotationPresent(annotationClass))
            .collect(Collectors.toList());
        CLASS_ANNOTATED_FIELDS_CACHE.get(clazz).put(annotationClass, annotatedFields);
        return annotatedFields;
    }

    /**
     * 获取类中第一个标有指定注解的字段
     *
     * @param clazz           类
     * @param annotationClass 注解类
     * @return 字段列表
     */
    public static Optional<Field> getAnnotatedField(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        List<Field> fields = getAnnotatedFields(clazz, annotationClass);
        if (fields.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(fields.get(0));
    }

    /**
     * 获取类中所有标有指定注解的方法
     *
     * @param clazz           类
     * @param annotationClass 注解类
     * @return 方法列表
     */
    public static List<Method> getAnnotatedMethods(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        if (CLASS_ANNOTATED_METHODS_CACHE.containsKey(clazz)) {
            Map<Class<? extends Annotation>, List<Method>> map = CLASS_ANNOTATED_METHODS_CACHE.get(clazz);
            if (map.containsKey(annotationClass)) {
                return map.get(annotationClass);
            }
        } else {
            CLASS_ANNOTATED_METHODS_CACHE.put(clazz, new ConcurrentReferenceHashMap<>(64));
        }
        Method[] methods = ReflectionUtils.getAllDeclaredMethods(clazz);
        List<Method> annotatedMethods = Arrays.stream(methods)
            .filter(method -> method.isAnnotationPresent(annotationClass))
            .collect(Collectors.toList());
        CLASS_ANNOTATED_METHODS_CACHE.get(clazz).put(annotationClass, annotatedMethods);
        return annotatedMethods;
    }

    /**
     * 参数注解信息类
     *
     * @param <T> 注解泛型
     */
    @Data
    @AllArgsConstructor(staticName = "of")
    public static class ParameterAnnotation<T extends Annotation> {

        /**
         * 注解对象
         */
        private T annotation;

        /**
         * 注解所在的参数位置
         */
        private int parameterIndex;
    }

    /**
     * 获取方法参数中的注解信息
     *
     * @param method          方法对象
     * @param annotationClass 注解类
     * @param <T>             泛型
     * @return 注解信息
     */
    public static <T extends Annotation> Optional<ParameterAnnotation<T>> getMethodParameterAnnotation(Method method, Class<T> annotationClass) {
        return Optional.ofNullable(METHOD_PARAMETER_ANNOTATION_OPTIONAL_MAP.computeIfAbsent(method, key -> findMethodParameterAnnotation(method, annotationClass))).map(TypeCastUtils::cast);
    }

    /**
     * 寻找参数上的注解
     *
     * @param method          方法
     * @param annotationClass 注解类
     * @param <T>             泛型
     * @return 参数注解信息对象
     */
    @SuppressWarnings("unchecked")
    private static <T extends Annotation> ParameterAnnotation<T> findMethodParameterAnnotation(Method method, Class<T> annotationClass) {
        Annotation[][] annotations = method.getParameterAnnotations();
        for (int i = 0, len = annotations.length; i < len; i++) {
            for (Annotation annotation : annotations[i]) {
                if (Objects.equals(annotation.annotationType(), annotationClass)) {
                    return ParameterAnnotation.of((T) annotation, i);
                }
            }
        }
        return null;
    }

    /**
     * 将对象中的null字符串替换为空字符串
     *
     * @param object 对象
     */
    public static void stripNullString2Empty(Object object) {
        Assert.notNull(object, "去除NULL字段的对象不能为空！");
        for (Field field : getClassAllDeclaredFields(object.getClass())) {
            if (!ClassOperationUtils.isStringClass(field.getType())) {
                continue;
            }

            if (!getFieldValue(field, object).isPresent()) {
                setFieldValue(field, object, StringPool.EMPTY);
            }
        }
    }
}
