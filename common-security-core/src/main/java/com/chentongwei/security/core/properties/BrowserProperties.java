package com.chentongwei.security.core.properties;

import com.chentongwei.security.core.constant.SecurityConstant;
import com.chentongwei.security.core.enums.FrameDisableStatus;
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
     * 若为跳转页面的形式，则登录成功后跳转到的页面，默认为上一次请求的url
     */
    private String loginSuccessPage;

    /**
     * 默认登录页面
     */
    private String loginPage = SecurityConstant.DEFAULT_LOGIN_PAGE_URL;

    /**
     * 默认注册页面
     */
    private String registerPage = SecurityConstant.DEFAULT_REGISTER_PAGE_URL;

    /**
     * 401状态码（未授权）所跳转的路径
     */
    private String unAuthorizedPage;

    /**
     * 记住我时长
     */
    private int rememberMeSeconds = 3600;

    /**
     * 允许iframe嵌套，1禁止，0允许
     */
    private Integer frameDisable = FrameDisableStatus.FORBIDDEN.status();

    /**
     * 绑定成功页面
     */
    private String bindSuccessPage;

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public String getLoginSuccessPage() {
        return loginSuccessPage;
    }

    public void setLoginSuccessPage(String loginSuccessPage) {
        this.loginSuccessPage = loginSuccessPage;
    }

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public String getRegisterPage() {
        return registerPage;
    }

    public void setRegisterPage(String registerPage) {
        this.registerPage = registerPage;
    }

    public int getRememberMeSeconds() {
        return rememberMeSeconds;
    }

    public void setRememberMeSeconds(int rememberMeSeconds) {
        this.rememberMeSeconds = rememberMeSeconds;
    }

    public String getUnAuthorizedPage() {
        return unAuthorizedPage;
    }

    public void setUnAuthorizedPage(String unAuthorizedPage) {
        this.unAuthorizedPage = unAuthorizedPage;
    }

    public Integer getFrameDisable() {
        return frameDisable;
    }

    public void setFrameDisable(Integer frameDisable) {
        this.frameDisable = frameDisable;
    }

    public String getBindSuccessPage() {
        return bindSuccessPage;
    }

    public void setBindSuccessPage(String bindSuccessPage) {
        this.bindSuccessPage = bindSuccessPage;
    }

}
