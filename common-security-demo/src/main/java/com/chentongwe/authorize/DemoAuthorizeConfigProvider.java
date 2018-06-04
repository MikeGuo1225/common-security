package com.chentongwe.authorize;

import com.chentongwei.security.core.authorize.AuthorizeConfigProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * @author chentongwei@bshf360.com 2018-05-17 16:53
 */
//@Component
//@Order(Integer.MAX_VALUE)
public class DemoAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Override
    public void config(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests().antMatchers("/user").hasRole("admin");
//        config.anyRequest().access("@rbacService.hasPermission(request,authentication)");
    }
}
