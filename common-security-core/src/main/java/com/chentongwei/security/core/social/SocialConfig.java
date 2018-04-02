package com.chentongwei.security.core.social;

import com.chentongwei.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * Social配置类
 *
 * @author chentongwei@bshf360.com 2018-04-02 09:52
 */
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
        // TODO 可配置
        repository.setTablePrefix("ctw_");
        /**
         * Encryptors 加解密工具，我们这里不加密
         */
        return repository;
    }

    /**
     * SpringSocial过滤器，加到BrowserSecurityConfig里，使之生效。
     *
     * @return
     */
    @Bean
    public SpringSocialConfigurer ctwSocialSecurityConfig() {
        return new CtwSpringSocialConfigurer(securityProperties.getSocial().getFilterProcessesUrl());
    }
}
