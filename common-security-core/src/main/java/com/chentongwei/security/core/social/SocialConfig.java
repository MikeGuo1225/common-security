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
import org.springframework.social.connect.web.ProviderSignInUtils;
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
        CtwSpringSocialConfigurer configurer = new CtwSpringSocialConfigurer(securityProperties.getSocial().getFilterProcessesUrl());
        /*
         * 自定义的注册页
         * 这个很关键，否则QQ登录后会说401，原因如下：
         * SocialAuthenticationProvider.authenticate()
         * String userId = toUserId(connection);
         * 此方法就是去我们建立的表中查询是否存在用户（查询条件在connection里，里面有token，openid等信息），若没有，则抛出异常
         * if (userId == null) {
         * throw new BadCredentialsException("Unknown access token");
         * }
         *  这个异常会被上层过滤器SocialAuthenticationFilter拦截到
         * 会进入doAuthentication的catch块，catch块抛出一个异常
         * throw new SocialAuthenticationRedirectException(buildSignupUrl(request));
         *
         * buildSignupUrl(request)方法就是返回注册页面，有个默认的注册页面signupUrl="/signup"，
         * 由于这个页面被我们权限拦截了，所以401
         *
         * 解决方案：
         * 写一个注册页，重新赋值给signupUrl，加到权限中，使之不拦截。也就是现在这种做法。
         */
        configurer.signupUrl(securityProperties.getBrowser().getRegisterPage());
        return configurer;
    }

    /**
     * 下面这个工具类主要解决两个问题：
     * 1、注册过程中如何拿到SpringSocial信息
     * 2、（注册接口能拿到userId（userconnection表的主键userId））注册完成后那怎么将这个userId传递给SpringSocial，
     * 让SpringSocial根据userId拿到用户信息，和注册时填写的基本信息一起保存到db中
     *
     * @return
     */
    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
        return new ProviderSignInUtils(connectionFactoryLocator, getUsersConnectionRepository(connectionFactoryLocator)) {

        };
    }
}
