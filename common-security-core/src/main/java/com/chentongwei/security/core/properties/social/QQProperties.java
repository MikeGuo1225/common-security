package com.chentongwei.security.core.properties.social;

import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * @author chentongwei@bshf360.com 2018-04-02 11:54
 */
public class QQProperties extends SocialProperties {

    private String providerId = "qq";

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
