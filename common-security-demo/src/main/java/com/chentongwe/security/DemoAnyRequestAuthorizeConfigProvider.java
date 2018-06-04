package com.chentongwe.security;

import com.chentongwei.security.core.authorize.AuthorizeConfigProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * @author chentongwei@bshf360.com 2018-06-01 18:49
 */
//@Component
//@Order(Integer.MAX_VALUE)
public class DemoAnyRequestAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Override
    public void config(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests().anyRequest().access("@rbacService.hasPermission(request,authentication)");
    }
}
