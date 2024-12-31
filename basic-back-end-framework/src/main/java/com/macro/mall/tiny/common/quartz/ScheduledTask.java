package com.macro.mall.tiny.common.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import java.io.Serializable;

/**
 * 可调度任务，框架最顶层的调度任务接口
 *
 * @author venus
 * @version 1
 * @date 2020/4/15 9:31 下午 星期三
 */
public interface ScheduledTask extends Serializable {



    /**
     * 任务执行方法
     *
     * @param context            任务执行上下文
     * @param applicationContext Spring容器
     * @throws JobExecutionException 任务执行异常
     */
    public void execute(JobExecutionContext context, ApplicationContext applicationContext) throws JobExecutionException;
}
