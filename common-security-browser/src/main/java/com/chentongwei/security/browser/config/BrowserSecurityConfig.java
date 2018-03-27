package com.chentongwei.security.browser.config;

import com.chentongwei.security.core.authentication.AbstractChannelSecurityConfig;
import com.chentongwei.security.core.constant.SecurityConstant;
import com.chentongwei.security.core.properties.SecurityProperties;
import com.chentongwei.security.core.validate.code.ValidateCodeSecurityConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 浏览器的安全配置
 *
 * @author chentongwei@bshf360.com 2018-03-26 10:32
 */
@Configuration
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        authenticationConfig(http);
        http
             // 配置验证码过滤器生效
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
        String permitUrls = securityProperties.getAuthorize().getPermitUrls();
        if (StringUtils.isNotEmpty(permitUrls) && StringUtils.isNotBlank(permitUrls)) {
            String[] urlArray = StringUtils.splitByWholeSeparator(permitUrls, ",");
            urls = new LinkedList<>(Arrays.asList(urlArray));
        }

        urls.add(SecurityConstant.DEFAULT_LOGIN_PAGE_URL);
        urls.add(SecurityConstant.DEFAULT_UNAUTHENTICATION_URL);
        urls.add(SecurityConstant.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*");
        urls.add(securityProperties.getBrowser().getLoginPage());
        return urls.toArray(new String[urls.size()]);
    }
}