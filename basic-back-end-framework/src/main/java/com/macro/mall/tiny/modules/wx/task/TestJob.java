package com.macro.mall.tiny.modules.wx.task;

import cn.hutool.core.date.LocalDateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;

/**
 * @author ztx
 * @version 1
 */
@Slf4j
public class TestJob extends QuartzJobBean {
    /**
     * Execute the actual job. The job data map will already have been
     * applied as bean property values by execute. The contract is
     * exactly the same as for the standard Quartz execute method.
     *
     * @param context
     * @see #execute
     */
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("TestJob1"+ LocalDateTimeUtil.formatNormal(LocalDateTime.now()));
    }
}
