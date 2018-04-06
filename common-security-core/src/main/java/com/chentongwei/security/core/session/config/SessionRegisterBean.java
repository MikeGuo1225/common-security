package com.chentongwei.security.core.session.config;

import com.chentongwei.security.core.properties.SecurityProperties;
import com.chentongwei.security.core.session.CtwExpiredSessionStrategy;
import com.chentongwei.security.core.session.CtwInvalidSessionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * Session相关注册Bean
 *
 * @author TongWei.Chen 2018-04-05 23:10:04
 */
@Configuration
public class SessionRegisterBean {

    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    public InvalidSessionStrategy invalidSessionStrategy() {
        return new CtwInvalidSessionStrategy(securityProperties.getSession().getSessionInvalidUrl());
    }

    @Bean
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
        return new CtwExpiredSessionStrategy(securityProperties.getSession().getSessionInvalidUrl());
    }

}
