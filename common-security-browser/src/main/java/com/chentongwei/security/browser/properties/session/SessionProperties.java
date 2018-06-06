package com.chentongwei.security.browser.properties.session;

/**
 * Session相关的配置
 *
 * @author TongWei.Chen 2018-04-06 21:36:11
 */
public class SessionProperties {

    /**
     * 同一个用户在系统中最大的session数，默认1
     */
    private int maximumSessions = 1;

    /**
     * session失效/被踢掉时跳转的地址，默认为系统默认登陆页
     */
    private String sessionInvalidUrl;

    public String getSessionInvalidUrl() {
        return sessionInvalidUrl;
    }

    public void setSessionInvalidUrl(String sessionInvalidUrl) {
        this.sessionInvalidUrl = sessionInvalidUrl;
    }

    public int getMaximumSessions() {
        return maximumSessions;
    }

    public void setMaximumSessions(int maximumSessions) {
        this.maximumSessions = maximumSessions;
    }

}
