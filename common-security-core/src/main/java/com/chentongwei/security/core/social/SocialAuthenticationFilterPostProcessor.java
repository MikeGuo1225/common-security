package com.chentongwei.security.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * 后处理器
 *
 * @author chentongwei@bshf360.com 2018-05-08 18:20
 */
public interface SocialAuthenticationFilterPostProcessor {

    void process(SocialAuthenticationFilter socialAuthenticationFilter);

}
