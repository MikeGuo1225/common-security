package com.chentongwei.security.validate.properties;

import com.chentongwei.security.validate.properties.authorize.AuthorizeProperties;
import com.chentongwei.security.validate.properties.code.ValidateCodeProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 总的Security配置类
 *
 * @author chentongwei@bshf360.com 2018-05-25 11:52
 */
@ConfigurationProperties(prefix = "com.chentongwei.security")
public class SecurityProperties {

    /** 验证码配置 */
    private ValidateCodeProperties code = new ValidateCodeProperties();

    /** 权限配置 */
    private AuthorizeProperties authorize = new AuthorizeProperties();

    public ValidateCodeProperties getCode() {
        return code;
    }

    public void setCode(ValidateCodeProperties code) {
        this.code = code;
    }

    public AuthorizeProperties getAuthorize() {
        return authorize;
    }

    public void setAuthorize(AuthorizeProperties authorize) {
        this.authorize = authorize;
    }
}
