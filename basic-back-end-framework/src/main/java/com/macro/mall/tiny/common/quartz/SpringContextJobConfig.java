package com.macro.mall.tiny.common.quartz;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Locale;

/**
 * job 上下文工具类
 *
 * @author ztx
 * @version 1
 */
public class SpringContextJobConfig implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static Object getBean(String beanName){
        return context.getBean(beanName);
    }

    public static Object getBeanByClazz(Class clazz){
        return context.getBean(clazz);
    }

    public static String getMessage(String key) {
        return context.getMessage(key, null, Locale.getDefault());
    }
}
