package com.chentongwei.security.core.properties;

import com.chentongwei.security.core.constant.SecurityConstant;
import com.chentongwei.security.core.enums.SessionInvalidType;

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
     * 达到最大session时是否阻止新的登陆请求，默认为false（不阻止），新的登陆会将老的登陆T掉，使之session失效
     */
    private boolean maxSessionsPreventsLogin;

    /**
     * session失效/被踢掉时跳转的地址，默认为系统默认登陆页
     */
    private String sessionInvalidUrl = SecurityConstant.DEFAULT_LOGIN_PAGE_URL;

    /**
     * session失效后返回的形式，JSON/VIEW
     */
    private SessionInvalidType sessionInvalidType = SessionInvalidType.JSON;

    public int getMaximumSessions() {
        return maximumSessions;
    }

    public void setMaximumSessions(int maximumSessions) {
        this.maximumSessions = maximumSessions;
    }

    public boolean isMaxSessionsPreventsLogin() {
        return maxSessionsPreventsLogin;
    }

    public void setMaxSessionsPreventsLogin(boolean maxSessionsPreventsLogin) {
        this.maxSessionsPreventsLogin = maxSessionsPreventsLogin;
    }

    public String getSessionInvalidUrl() {
        return sessionInvalidUrl;
    }

    public void setSessionInvalidUrl(String sessionInvalidUrl) {
        this.sessionInvalidUrl = sessionInvalidUrl;
    }

    public SessionInvalidType getSessionInvalidType() {
        return sessionInvalidType;
    }

    public void setSessionInvalidType(SessionInvalidType sessionInvalidType) {
        this.sessionInvalidType = sessionInvalidType;
    }
}
