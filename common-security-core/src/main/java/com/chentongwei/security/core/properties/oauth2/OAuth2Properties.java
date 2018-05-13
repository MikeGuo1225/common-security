package com.chentongwei.security.core.properties.oauth2;

/**
 * @author chentongwei@bshf360.com 2018-05-09 10:30
 */
public class OAuth2Properties {

    /**
     * 刷新token的url，比如localhost:8080
     */
    private String refreshTokenUrl = "http://localhost:80";

    /**
     * jwt密签key
     */
    private String jwtSigningKey = "ctw";

    private OAuth2ClientProperties client;

    public String getRefreshTokenUrl() {
        return refreshTokenUrl;
    }

    public void setRefreshTokenUrl(String refreshTokenUrl) {
        this.refreshTokenUrl = refreshTokenUrl;
    }

    public OAuth2ClientProperties getClient() {
        return client;
    }

    public void setClient(OAuth2ClientProperties client) {
        this.client = client;
    }

    public String getJwtSigningKey() {
        return jwtSigningKey;
    }

    public void setJwtSigningKey(String jwtSigningKey) {
        this.jwtSigningKey = jwtSigningKey;
    }
}
