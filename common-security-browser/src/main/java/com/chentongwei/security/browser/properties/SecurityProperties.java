package com.chentongwei.security.browser.properties;

import com.chentongwei.security.browser.properties.logout.LogoutProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 总的Browser模块Security配置类
 *
 * @author chentongwei@bshf360.com 2018-05-25 11:52
 */
@ConfigurationProperties(prefix = "com.chentongwei.security")
public class SecurityProperties {

    /** 退出登录基本配置 */
    private LogoutProperties logout = new LogoutProperties();

    public LogoutProperties getLogout() {
        return logout;
    }

    public void setLogout(LogoutProperties logout) {
        this.logout = logout;
    }
}
