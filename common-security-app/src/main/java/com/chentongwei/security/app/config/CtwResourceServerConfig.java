package com.chentongwei.security.app.config;

import com.chentongwei.security.app.social.OpenIdAuthenticationSecurityConfig;
import com.chentongwei.security.app.util.PermitUrlsUtil;
import com.chentongwei.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.chentongwei.security.core.constant.SecurityConstant;
import com.chentongwei.security.core.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;

import java.util.LinkedList;
import java.util.List;

/**
 * 资源服务器配置
 *
 * @author chentongwei@bshf360.com 2018-04-08 11:37
 */
@Configuration
@EnableResourceServer
public class CtwResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private AuthenticationSuccessHandler ctwAuthenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler ctwAuthenticationFailureHandler;
    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private OpenIdAuthenticationSecurityConfig openIdAuthenticationSecurityConfig;

    /**
     * Spring Social相关
     */
    @Autowired
    private SpringSocialConfigurer ctwSocialSecurityConfig;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 表单登录
        http.formLogin()
            // 默认表单登录页
            .loginPage(SecurityConstant.DEFAULT_UNAUTHENTICATION_URL)
            // 登录接口
            .loginProcessingUrl(SecurityConstant.DEFAULT_LOGIN_PROCESSING_URL_FORM)
            .successHandler(ctwAuthenticationSuccessHandler)
            .failureHandler(ctwAuthenticationFailureHandler)
            .and()
            .apply(validateCodeSecurityConfig)
            .and()
            .apply(smsCodeAuthenticationSecurityConfig)
            .and()
            .apply(ctwSocialSecurityConfig)
            .and()
            .apply(openIdAuthenticationSecurityConfig)
            .and()
            // 权限设置
            .authorizeRequests()
            // 任何请求都必须经过身份认证，排除如下
            .antMatchers(
                    PermitUrlsUtil.getPermitUrls()
            ).permitAll()
            // 任何请求
            .anyRequest()
            // 都必须经过身份认证
            .authenticated()
            .and()
            // 先加上这句话，否则登录的时候会出现403错误码，Could not verify the provided CSRF token because your session was not found.
            .csrf().disable();
    }

}
