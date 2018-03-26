package com.chentongwei.security.core.authentication;

import com.chentongwei.security.core.constant.SecurityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * 封装处理请求成功/失败的操作
 *
 * @author chentongwei@bshf360.com 2018-03-26 10:35
 */
public class AbstractChannelSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationSuccessHandler ctwAuthenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler ctwAuthenticationFailureHandler;

    protected void authenticationConfig(HttpSecurity http) throws Exception {
        // 表单登录
        http.formLogin()
            // 默认表单登录页
            .loginPage(SecurityConstant.DEFAULT_UNAUTHENTICATION_URL)
            // 登录接口
            .loginProcessingUrl(SecurityConstant.DEFAULT_LOGIN_PROCESSING_URL_FORM)
            .successHandler(ctwAuthenticationSuccessHandler)
            .failureHandler(ctwAuthenticationFailureHandler);
    }
}
