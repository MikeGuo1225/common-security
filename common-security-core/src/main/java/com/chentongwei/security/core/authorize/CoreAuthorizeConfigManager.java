package com.chentongwei.security.core.authorize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author chentongwei@bshf360.com 2018-05-17 16:40
 */
@Component
public class CoreAuthorizeConfigManager implements AuthorizeConfigManager {

    @Autowired
    private List<AuthorizeConfigProvider> authorizeConfigProviders;

    @Override
    public void config(HttpSecurity httpSecurity) throws Exception {
        for (AuthorizeConfigProvider provider : authorizeConfigProviders) {
            provider.config(httpSecurity);
        }
    }
}
