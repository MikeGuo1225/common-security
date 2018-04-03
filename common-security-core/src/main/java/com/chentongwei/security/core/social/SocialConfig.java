package com.chentongwei.security.core.social;

import com.chentongwei.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * Social配置类
 *
 * @author chentongwei@bshf360.com 2018-04-02 09:52
 */
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

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

}
