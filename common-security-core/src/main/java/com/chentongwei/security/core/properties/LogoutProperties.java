package com.chentongwei.security.core.properties;

import com.chentongwei.security.core.constant.SecurityConstant;

/**
 * 退出功能配置
 *
 * @author TongWei.Chen 2018-04-06 23:49:33
 */
public class LogoutProperties {

    /**
     * 退出url，springsecurity默认是/logout
     */
    private String logoutUrl = "/logout";

    /**
     * 退出成功后跳转的路径
     */
    private String logoutSuccessUrl = SecurityConstant.DEFAULT_LOGIN_PAGE_URL;

    public String getLogoutUrl() {
        return logoutUrl;
    }

    public void setLogoutUrl(String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }

    public String getLogoutSuccessUrl() {
        return logoutSuccessUrl;
    }

    public void setLogoutSuccessUrl(String logoutSuccessUrl) {
        this.logoutSuccessUrl = logoutSuccessUrl;
    }
}
