package com.chentongwei.security.app.authorize;

import com.chentongwei.security.app.jwt.filter.JwtAuthenticationTokenFilter;
import com.chentongwei.security.core.authorize.AuthorizeConfigProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * APP（前后端分离）授权核心配置
 *
 * @author chentongwei@bshf360.com 2018-06-07 20:22
 */
@Component
@Order(Integer.MIN_VALUE + 2)
public class AppAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Override
    public void config(HttpSecurity httpSecurity) throws Exception {
        // 基于token，所以不需要session
        httpSecurity
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
