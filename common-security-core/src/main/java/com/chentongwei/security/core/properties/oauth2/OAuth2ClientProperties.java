package com.chentongwei.security.core.properties.oauth2;

/**
 * @author chentongwei@bshf360.com 2018-05-09 10:30
 */
public class OAuth2ClientProperties {
    private String clientId;

    private String clientSecret;

    // 默认token失效一小时
    private int accessTokenValidSeconds = 7200;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public int getAccessTokenValidSeconds() {
        return accessTokenValidSeconds;
    }

    public void setAccessTokenValidSeconds(int accessTokenValidSeconds) {
        this.accessTokenValidSeconds = accessTokenValidSeconds;
    }
}
