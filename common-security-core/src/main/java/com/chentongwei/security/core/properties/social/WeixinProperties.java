package com.chentongwei.security.core.properties.social;

import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * 微信登录配置信息
 *
 * @author chentongwei@bshf360.com 2018-04-04 13:28
 */
public class WeixinProperties extends SocialProperties {

    /**
     * 第三方id，用来决定发起第三方登录的url，默认是weixin
     */
    private String providerId = "weixin";

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
