package com.chentongwei.security.browser.properties;

import com.chentongwei.security.browser.properties.frame.FrameProperties;
import com.chentongwei.security.browser.properties.logout.LogoutProperties;
import com.chentongwei.security.browser.properties.rememberme.RememberMeProperties;
import com.chentongwei.security.browser.properties.session.SessionProperties;
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
    /** 记住我基本配置 */
    private RememberMeProperties rememberme = new RememberMeProperties();
    /** iframe的基本配置 */
    private FrameProperties frame = new FrameProperties();
    /** session的基本配置 */
    private SessionProperties session = new SessionProperties();

    public LogoutProperties getLogout() {
        return logout;
    }

    public void setLogout(LogoutProperties logout) {
        this.logout = logout;
    }

    public RememberMeProperties getRememberme() {
        return rememberme;
    }

    public void setRememberme(RememberMeProperties rememberme) {
        this.rememberme = rememberme;
    }

    public FrameProperties getFrame() {
        return frame;
    }

    public void setFrame(FrameProperties frame) {
        this.frame = frame;
    }

    public SessionProperties getSession() {
        return session;
    }

    public void setSession(SessionProperties session) {
        this.session = session;
    }
}
