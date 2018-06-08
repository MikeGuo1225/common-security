package com.chentongwei.security.app.properties.oauth2;

/**
 * 客户端基本配置
 *
 * @author chentongwei@bshf360.com 2018-06-08 10:52
 */
public class ClientProperties {

    /** 客户端id */
    private String clientId;

    /** 客户端密钥 */
    private String clientSecret;

    /** 默认token失效一小时 */
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
