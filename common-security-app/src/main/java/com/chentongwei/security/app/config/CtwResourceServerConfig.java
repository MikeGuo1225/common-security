package com.chentongwei.security.app.config;

import com.chentongwei.security.app.social.OpenIdAuthenticationSecurityConfig;
import com.chentongwei.security.app.util.PermitUrlsUtil;
import com.chentongwei.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.chentongwei.security.core.authorize.AuthorizeConfigManager;
import com.chentongwei.security.core.constant.SecurityConstant;
import com.chentongwei.security.core.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;

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

    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;

    /**
     * Spring Social相关
     */
    @Autowired
    private SpringSocialConfigurer ctwSocialSecurityConfig;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 表单登录
        http.formLogin()
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
            // 配置/user请求，只有配置了admin角色后才能访问。
            .antMatchers(HttpMethod.GET, "/user/**").hasRole("admin")
            .antMatchers(HttpMethod.POST, "/user/**").hasRole("admin1")
            .antMatchers(HttpMethod.PUT, "/user").hasAnyAuthority("write")
            .antMatchers(HttpMethod.DELETE, "/user").access("hasRole('ADMIN') and hasIpAddress('192.168.73.251')")
            // 任何请求
            .anyRequest()
            // 都必须经过身份认证
            .authenticated()
            .and()
            // 先加上这句话，否则登录的时候会出现403错误码，Could not verify the provided CSRF token because your session was not found.
            .csrf().disable();

        authorizeConfigManager.config(http.authorizeRequests());
    }

}
