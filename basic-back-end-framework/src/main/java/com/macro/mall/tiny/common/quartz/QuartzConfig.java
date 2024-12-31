package com.macro.mall.tiny.common.quartz;

import cn.hutool.extra.spring.SpringUtil;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSourceScriptDatabaseInitializer;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

/**
 * Job详情配置
 *
 * @author venus
 * @version 1
 * @date 2020/4/3 3:07 下午 星期五
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.quartz", name = "auto-startup")
@Import({SpringUtil.class})
public class QuartzConfig {

    @Value("${spring.application.name:'unknown'}")
    private String applicationName;

    /**
     * Framework框架顶层Job
     *
     * @return JobBean
     */
    @Bean
    public JobDetailFactoryBean frameworkJobDetail() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setDurability(true);
        factoryBean.setName(FrameworkJob.JOB_KEY.getName());
        factoryBean.setGroup(FrameworkJob.JOB_KEY.getGroup());
        factoryBean.setJobClass(FrameworkJob.class);
        factoryBean.setDescription("框架顶级任务");
        return factoryBean;
    }

    /**
     * Quartz数据源自定义
     *
     * @param quartzDataSourceHolder quartz数据源持有器
     * @param properties             属性配置
     * @return 数据源初始化器
     */
    @Bean
    public QuartzDataSourceScriptDatabaseInitializer quartzDataSourceInitializer(QuartzDataSourceHolder quartzDataSourceHolder, QuartzProperties properties) {
        return new QuartzDataSourceScriptDatabaseInitializer(quartzDataSourceHolder.getHikariDataSource(), properties);
    }

    /**
     * quartz数据源持有器
     *
     * @param quartzConfigProperties 属性配置
     * @return quartz数据源持有器
     */
    @Bean
    public QuartzDataSourceHolder quartzDataSourceHolder(QuartzConfigProperties quartzConfigProperties) {
        // 构建数据源
        HikariDataSource dataSource = quartzConfigProperties.getDataSource().initializeDataSourceBuilder().type(HikariDataSource.class).build();
        dataSource.setMaximumPoolSize(16);
        dataSource.setMinimumIdle(4);
        dataSource.setPoolName(this.applicationName + "-定时任务数据库连接池");
        dataSource.setMaxLifetime(1800000);
        dataSource.setConnectionTimeout(30 * 1000);
        dataSource.setIdleTimeout(100000);
        dataSource.setKeepaliveTime(60000);
        dataSource.setConnectionTestQuery("SELECT 1");
        return new QuartzDataSourceHolder(dataSource);
    }

    /**
     * 数据源持有器
     */
    @Data
    @AllArgsConstructor
    public static class QuartzDataSourceHolder {

        /**
         * 数据源
         */
        private HikariDataSource hikariDataSource;
    }


}
