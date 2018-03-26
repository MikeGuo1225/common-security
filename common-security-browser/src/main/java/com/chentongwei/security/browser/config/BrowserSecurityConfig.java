package com.chentongwei.security.browser.config;

import com.chentongwei.security.core.constant.SecurityConstant;
import com.chentongwei.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author chentongwei@bshf360.com 2018-03-26 10:32
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 表单登录
        http.formLogin()
             // 默认表单登录页
            .loginPage("/authentication/require")
            .and()
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
                .authenticated();
    }
}