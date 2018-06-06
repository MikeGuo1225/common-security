package com.chentongwei.security.browser.config;

import com.chentongwei.security.browser.properties.SecurityProperties;
import com.chentongwei.security.browser.session.BrowserExpiredSessionStrategy;
import com.chentongwei.security.browser.session.BrowserInvalidSessionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * Session 管理注册bean
 *
 * @author chentongwei@bshf360.com 2018-06-06 13:46
 */
@Configuration
public class SessionRegisterBean {

    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    public InvalidSessionStrategy invalidSessionStrategy() {
        return new BrowserInvalidSessionStrategy(securityProperties.getSession().getSessionInvalidUrl());
    }

    @Bean
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
        return new BrowserExpiredSessionStrategy(securityProperties.getSession().getSessionInvalidUrl());
    }
}
