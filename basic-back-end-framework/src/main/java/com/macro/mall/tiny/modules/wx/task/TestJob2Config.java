package com.macro.mall.tiny.modules.wx.task;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ztx
 * @version 1
 */
@Configuration
public class TestJob2Config {
    /**
     * 定时任务1：
     * 同步用户信息Job（任务详情）
     */
    @Bean
    public JobDetail test2JobDetail(){
        return JobBuilder.newJob(TestJob2.class)
            .storeDurably() //必须调用该方法，添加任务
            .build();
    }

    @Bean
    public Trigger test2Trigger(){
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/1 * * * * ?"); //配置任务频率
        return TriggerBuilder.newTrigger()
            .forJob(test2JobDetail())
            .withIdentity("1","2")
            .withSchedule(cronScheduleBuilder) //对触发器配置任务
            .build();
    }
}
