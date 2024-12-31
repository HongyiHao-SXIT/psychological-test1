package com.macro.mall.tiny.common.quartz;

import lombok.Data;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 定时任务Quartz配置
 *
 * @author venus
 * @version 1
 * @date 2020/4/17 9:06 下午 星期五
 */
@Component
@ConfigurationProperties(prefix = "framework.quartz")
@Data
public class QuartzConfigProperties {

    /**
     * Quartz数据源配置
     */
    private DataSourceProperties dataSource = new DataSourceProperties();
}
