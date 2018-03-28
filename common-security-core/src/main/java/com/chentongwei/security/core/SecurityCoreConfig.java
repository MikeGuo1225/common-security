package com.chentongwei.security.core;

import com.chentongwei.security.core.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 加载配置
 *
 * @author chentongwei@bshf360.com 2018-03-26 11:25
 */
@Configuration
@PropertySource("classpath:security.properties")
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {
}
