package com.chentongwei.security.browser.authorize;

import com.chentongwei.security.browser.logout.BrowserLogoutSuccessHandler;
import com.chentongwei.security.browser.properties.SecurityProperties;
import com.chentongwei.security.core.authorize.AuthorizeConfigProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

/**
 * 浏览器（前后不分离）的一些核心配置
 *
 * @author chentongwei@bshf360.com 2018-06-04 16:05
 */
@Component
@Order(Integer.MIN_VALUE + 2)
public class BrowserAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void config(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                    .antMatchers(securityProperties.getLogout().getLogoutUrl()).permitAll()
                .and()
                .logout()
                    .logoutUrl(securityProperties.getLogout().getLogoutUrl())
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessHandler(new BrowserLogoutSuccessHandler(securityProperties.getLogout().getLogoutSuccessUrl()));
    }
}
