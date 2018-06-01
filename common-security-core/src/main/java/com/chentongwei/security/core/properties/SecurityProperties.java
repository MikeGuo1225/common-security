package com.chentongwei.security.core.properties;

import com.chentongwei.security.core.properties.authentication.AuthenticationProperties;
import com.chentongwei.security.core.properties.authorize.AuthorizeProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 总的Core模块Security配置类
 *
 * @author chentongwei@bshf360.com 2018-05-25 11:52
 */
@ConfigurationProperties(prefix = "com.chentongwei.security")
public class SecurityProperties {

    /** 授权模块配置 */
    private AuthorizeProperties authorize = new AuthorizeProperties();

    /** 认证模块配置 */
    private AuthenticationProperties authentication = new AuthenticationProperties();

    public AuthorizeProperties getAuthorize() {
        return authorize;
    }

    public void setAuthorize(AuthorizeProperties authorize) {
        this.authorize = authorize;
    }

    public AuthenticationProperties getAuthentication() {
        return authentication;
    }

    public void setAuthentication(AuthenticationProperties authentication) {
        this.authentication = authentication;
    }
}
