package com.chentongwei.security.app.config;

import com.chentongwei.security.app.authentication.AppAuthenticationFailureHandler;
import com.chentongwei.security.app.authentication.AppAuthenticationSuccessHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * APP模块认证处理器配置
 *
 * @author TongWei.Chen 2018-06-10 13:52:46
 */
@Configuration
public class AppAuthenticationHandlerConfig {

    @Bean(name = "authenticationSuccessHandler")
    @ConditionalOnProperty(prefix = "com.chentongwei.security.app.success.handler", name = "enable", matchIfMissing = true)
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
       return new AppAuthenticationSuccessHandler();
    }

    @Bean(name = "authenticationFailureHandler")
    @ConditionalOnProperty(prefix = "com.chentongwei.security.app.failure.handler", name = "enable", matchIfMissing = true)
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new AppAuthenticationFailureHandler();
    }
}
