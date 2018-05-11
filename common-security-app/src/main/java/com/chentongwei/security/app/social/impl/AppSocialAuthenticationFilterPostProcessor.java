package com.chentongwei.security.app.social.impl;

import com.chentongwei.security.core.social.SocialAuthenticationFilterPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * APP后处理器的具体实现，指定登录成功后跳转到我们自定义的成功处理器，而不是默认的
 *
 * @author chentongwei@bshf360.com 2018-05-08 18:24
 */
@Component
public class AppSocialAuthenticationFilterPostProcessor implements SocialAuthenticationFilterPostProcessor {

    @Autowired
    private AuthenticationSuccessHandler ctwAuthenticationSuccessHandler;

    @Override
    public void process(SocialAuthenticationFilter socialAuthenticationFilter) {
        socialAuthenticationFilter.setAuthenticationSuccessHandler(ctwAuthenticationSuccessHandler);
    }
}
