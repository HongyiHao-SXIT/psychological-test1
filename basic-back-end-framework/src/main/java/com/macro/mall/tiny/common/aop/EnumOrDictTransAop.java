package com.macro.mall.tiny.common.aop;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.macro.mall.tiny.common.api.CommonPage;
import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.common.model.BaseModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 枚举类、字典翻译类处理器
 *
 * @author ztx
 * @version 1
 */

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class EnumOrDictTransAop {

    private final ObjectMapper objectMapper;

    /**
     * 切点
     */
    @Pointcut("@annotation(com.macro.mall.tiny.common.aop.EnumOrDictTrans)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object run(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取方法参数值数组
        Object[] args = joinPoint.getArgs();


        log.info("请求参数为{}", JSONUtil.toJsonStr(args));
        //动态修改其参数
        //注意，如果调用joinPoint.proceed()方法，则修改的参数值不会生效，必须调用joinPoint.proceed(Object[] args)
        Object result = joinPoint.proceed(args);
        log.info("响应结果为{}", result);

        if (args.length > 0 && args[0] instanceof BaseModel) {
            BaseModel baseModel = (BaseModel) args[0];

            List<JSONObject> items = new ArrayList<>();
            if (result instanceof CommonResult && ((CommonResult<?>) result).isSuccess()) {
                if(((CommonResult<?>) result).getData() instanceof CommonPage){
                    CommonPage commonPage = (CommonPage) ((CommonResult<?>) result).getData();
                    List list = commonPage.getList();
                    for (Object record : list) {
                        items.add(handle(record));
                    }
                    commonPage.setList(items);
                    return CommonResult.success(commonPage);
                }
                if (((CommonResult) result).getData() instanceof IPage) {
                    IPage iPage = (IPage<?>) ((CommonResult) result).getData();
                    for (Object record : iPage.getRecords()) {
                        items.add(handle(record));
                    }
                    iPage.setRecords(items);
                }
                if (((CommonResult) result).getData() instanceof List) {
                    List list = (List) ((CommonResult<?>) result).getData();
                    for (Object record : list) {
                        items.add(handle(record));
                    }
                    return CommonResult.success(items);
                }
                if (((CommonResult) result).getData() instanceof Object) {
                    Object data = ((CommonResult) result).getData();
                    JSONObject handle = handle(data);
                    return CommonResult.success(handle);
                }
            }

        }
        //如果这里不返回result，则目标对象实际返回值会被置为null
        return result;
    }

    /**
     * 处理器
     *
     * @param record 记录
     * @return 处理结果
     */
    private JSONObject handle(Object record) {
        String json = null;
        try {
            json = objectMapper.writeValueAsString(record);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        JSONObject itemObj = JSONObject.parseObject(json);
        for (Field field : ReflectUtil.getFields(record.getClass())) {
            if (field.getType().isEnum()) {
                try {
                    field.setAccessible(true);
                    Class<Enum> clazz = (Class<Enum>) Class.forName(field.getType().getName());
                    Enum[] enumConstants = clazz.getEnumConstants();
                    Method getName = clazz.getMethod("getName");
                    for (Enum enumConstant : enumConstants) {
                        if(Objects.nonNull(field.get(record))
                            && StringUtils.equals(enumConstant.name(),field.get(record).toString())){
                            itemObj.put(field.getName() + "Name", getName.invoke(enumConstant).toString());
                        }

                    }

                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
        return itemObj;
    }


}
