package com.chentongwei.security.browser.properties.logout;

/**
 * 退出登录基本配置
 *
 * @author chentongwei@bshf360.com 2018-06-04 16:43
 */
public class LogoutProperties {

    /** 退出登录接口 */
    private String logoutUrl = "/logout";

    /** 退出登录成功后跳转的url */
    private String logoutSuccessUrl;

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
