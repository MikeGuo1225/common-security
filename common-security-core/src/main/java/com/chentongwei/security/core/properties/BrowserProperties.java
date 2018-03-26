package com.chentongwei.security.core.properties;

import com.chentongwei.security.core.constant.SecurityConstant;
import com.chentongwei.security.core.enums.LoginType;

/**
 * 浏览器的一些配置
 *
 * @author chentongwei@bshf360.com 2018-03-26 11:20
 */
public class BrowserProperties {

    /**
     * 登录方式，跳转页面/JSON
     */
    private LoginType loginType = LoginType.JSON;

    /**
     * 默认登录页面
     */
    private String loginPage = SecurityConstant.DEFAULT_LOGIN_PAGE_URL;

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }
}
