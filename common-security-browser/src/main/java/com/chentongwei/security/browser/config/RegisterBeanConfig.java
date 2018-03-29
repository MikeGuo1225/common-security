package com.chentongwei.security.browser.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 注册bean的配置
 *
 * @author chentongwei@bshf360.com 2018-03-26 17:25
 */
@Configuration
public class RegisterBeanConfig {

    /**
     * 加密用的
     *
     * 默认注册一个加密bean
     * 这样在MyUserDetailsService里才能用PasswordEncoder
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
