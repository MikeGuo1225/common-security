package com.chentongwei.security.core.config;

import com.chentongwei.security.core.authentication.CoreAuthenticationFailureHandler;
import com.chentongwei.security.core.authentication.CoreAuthenticationSuccessHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * 处理成功/失败的配置类
 *
 * @author chentongwei@bshf360.com 2018-05-28 18:32
 */
@Configuration
public class CoreAuthenticationHandlerConfig {

    /**
     * 成功处理器
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "authenticationSuccessHandler")
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CoreAuthenticationSuccessHandler();
    }

    /**
     * 失败处理器
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "authenticationFailureHandler")
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CoreAuthenticationFailureHandler();
    }
}