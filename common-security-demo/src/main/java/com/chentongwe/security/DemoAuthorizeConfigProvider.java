package com.chentongwe.security;

import com.chentongwei.security.core.authorize.AuthorizeConfigProvider;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

/**
 * @author chentongwei@bshf360.com 2018-06-01 18:49
 */
@Component
@Order(Integer.MAX_VALUE - 100)
public class DemoAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Override
    public void config(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests().antMatchers("/hello2").hasRole("admin");
    }
}
