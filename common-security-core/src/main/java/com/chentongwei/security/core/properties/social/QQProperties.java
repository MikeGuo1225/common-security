package com.chentongwei.security.core.properties.social;

import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * @author chentongwei@bshf360.com 2018-04-02 11:54
 */
public class QQProperties extends SocialProperties {

    /**
     * 标识
     */
    private String providerId = "qq";

    /**
     * 自动注册；false缺省值，默认不自动。true：自动
     */
    private String autoSignUp = "false";

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getAutoSignUp() {
        return autoSignUp;
    }

    public void setAutoSignUp(String autoSignUp) {
        this.autoSignUp = autoSignUp;
    }
}
