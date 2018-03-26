package com.chentongwei.security.browser.config;

import com.chentongwei.security.core.authentication.AbstractChannelSecurityConfig;
import com.chentongwei.security.core.constant.SecurityConstant;
import com.chentongwei.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 浏览器的安全配置
 *
 * @author chentongwei@bshf360.com 2018-03-26 10:32
 */
@Configuration
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        authenticationConfig(http);
        http
         // 权限设置
        .authorizeRequests()
             // 任何请求都必须经过身份认证，排除如下
            .antMatchers(
                    SecurityConstant.DEFAULT_LOGIN_PAGE_URL,
                    SecurityConstant.DEFAULT_UNAUTHENTICATION_URL,
                    securityProperties.getBrowser().getLoginPage()
            ).permitAll()
             // 任何请求
            .anyRequest()
             // 都必须经过身份认证
            .authenticated()
        .and()
         // 先加上这句话，否则登录的时候会出现403错误码，Could not verify the provided CSRF token because your session was not found.
        .csrf().disable();
    }

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