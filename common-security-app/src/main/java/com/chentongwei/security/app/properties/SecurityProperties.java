package com.chentongwei.security.app.properties;

import com.chentongwei.security.app.properties.oauth2.JwtProperties;
import com.chentongwei.security.app.properties.oauth2.Oauth2Properties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * APP模块的统一配置
 *
 * @author chentongwei@bshf360.com 2018-06-07 19:51
 */
@ConfigurationProperties(prefix = "com.chentongwei.security")
public class SecurityProperties {

    /** oauth2的基本配置 */
//    private Oauth2Properties oauth2 = new Oauth2Properties();

    private JwtProperties jwt = new JwtProperties();

    public JwtProperties getJwt() {
        return jwt;
    }

    public void setJwt(JwtProperties jwt) {
        this.jwt = jwt;
    }

    //    public Oauth2Properties getOauth2() {
//        return oauth2;
//    }

//    public void setOauth2(Oauth2Properties oauth2) {
//        this.oauth2 = oauth2;
//    }
}
