package com.chentongwei.security.core.properties.authorize;

/**
 * 权限模块配置
 *
 * @author chentongwei@bshf360.com 2018-05-31 13:27
 */
public class AuthorizeProperties {

    /** 无需权限即可访问的url */
    private String permitUrls;

    /** 访问无权限的接口时，跳转到哪个页面 */
    private String unAuthorizePage;

    public String getPermitUrls() {
        return permitUrls;
    }

    public void setPermitUrls(String permitUrls) {
        this.permitUrls = permitUrls;
    }

    public String getUnAuthorizePage() {
        return unAuthorizePage;
    }

    public void setUnAuthorizePage(String unAuthorizePage) {
        this.unAuthorizePage = unAuthorizePage;
    }
}
