package com.macro.mall.tiny.config;

import com.macro.mall.tiny.conver.EnumConverterFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 注册配置
 *
 * @author ztx
 * @version 1
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new EnumConverterFactory());
    }
}
