package com.chentongwei.security.validate;

import com.chentongwei.security.validate.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 安全模块核心配置
 *
 * @author chentongwei@bshf360.com 2018-05-25 11:31
 */
@Configuration
@PropertySource("classpath:security.properties")
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityValidateConfig {

    /**
     * 加密用的
     *
     * 默认注册一个加密bean
     * 这样在UserDetailsService里才能用PasswordEncoder
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
