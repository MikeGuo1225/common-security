package com.chentongwe.config;

import com.chentongwei.security.validate.config.ValidateCodeSecurityConfig;
import com.chentongwei.security.validate.constants.ValidateCodeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.LinkedList;
import java.util.List;

/**
 * 浏览器的安全配置
 *
 * @author chentongwei@bshf360.com 2018-03-26 10:32
 */
//@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    AuthenticationSuccessHandler authenticationSuccessHandler;
    /**
     * 验证码
     */
    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginProcessingUrl("/login")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
                .apply(validateCodeSecurityConfig)
                .and()
        // 权限设置
        .authorizeRequests()
            // 任何请求都必须经过身份认证，排除如下
            .antMatchers(
                    getPermitUrls()
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
     * 获取所有的无需权限即可访问的urls
     * @return
     */
    private String[] getPermitUrls() {
        List<String> urls = new LinkedList<>();
        urls.add("/login.html");
        urls.add(ValidateCodeConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*");
        return urls.toArray(new String[urls.size()]);
    }
}