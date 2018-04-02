package com.chentongwei.security.core.social;

import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @author chentongwei@bshf360.com 2018-04-02 13:01
 */
public class CtwSpringSocialConfigurer extends SpringSocialConfigurer {

    private String filterProcessesUrl;

    public CtwSpringSocialConfigurer(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

}
