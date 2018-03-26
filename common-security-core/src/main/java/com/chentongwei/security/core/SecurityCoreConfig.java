package com.chentongwei.security.core;

import com.chentongwei.security.core.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author chentongwei@bshf360.com 2018-03-26 11:25
 */
@Configuration
@PropertySource("classpath:me.properties")
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {
}
