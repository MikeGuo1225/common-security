package com.chentongwei.security.core.properties;

/**
 * 权限认证的配置
 *
 * @author chentongwei@bshf360.com 2018-03-26 17:13
 */
public class AuthorizeProperties {

    private String permitUrls;

    public String getPermitUrls() {
        return permitUrls;
    }

    public void setPermitUrls(String permitUrls) {
        this.permitUrls = permitUrls;
    }
}
