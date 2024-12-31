package com.macro.mall.tiny.modules.wx.task;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.macro.mall.tiny.common.constant.FrameworkConstants;
import com.macro.mall.tiny.common.utils.DateTimeUtils;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ztx
 * @version 1
 */
@Configuration
public class TestJobConfig {
    /**
     * 定时任务1：
     * 同步用户信息Job（任务详情）
     */
    @Bean
    public JobDetail testJobDetail() {
        return JobBuilder.newJob(TestJob.class)
            .storeDurably() //必须调用该方法，添加任务
            .build();
    }

    @Bean
    public Trigger testTrigger() {
        return TriggerBuilder.newTrigger()
            .forJob(testJobDetail())
            .withIdentity("1", "1")
            .startAt(DateTimeUtils.toDate(LocalDateTimeUtil.parse("2023-06-30 17:37:00", FrameworkConstants.DATE_TIME_PATTERN)))
            .build();
    }
}
