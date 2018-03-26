package com.chentongwei.security.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 总的配置
 *
 * @author chentongwei@bshf360.com 2018-03-26 11:24
 */
@ConfigurationProperties(prefix = "com.chentongwei.security")
public class SecurityProperties {

    private BrowserProperties browser = new BrowserProperties();

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }
}
