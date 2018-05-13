package com.chentongwei.security.core.properties.social;

/**
 * @author chentongwei@bshf360.com 2018-04-02 11:55
 */
public class SocialProperties {

    /**
     * QQ/微信等登录URL
     */
    private String filterProcessesUrl = "/auth";

    /**
     * 自动注册；false缺省值，默认不自动。true：自动
     */
    private String autoSignUp = "false";

    /**
     * TODO
     * 不自动注册，跳转到的url，QQ登录后发现未注册（db没数据），则跳转到的注册的url
     */
    private String defaultRegistUrl;

    /**
     * 表前缀
     */
    private String tablePrefix;

    /**
     * QQ互联配置
     */
    private QQProperties qq = new QQProperties();

    /**
     * 微信配置
     */
    private WeixinProperties weixin = new WeixinProperties();

    public QQProperties getQq() {
        return qq;
    }

    public void setQq(QQProperties qq) {
        this.qq = qq;
    }

    public String getFilterProcessesUrl() {
        return filterProcessesUrl;
    }

    public void setFilterProcessesUrl(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    public String getAutoSignUp() {
        return autoSignUp;
    }

    public void setAutoSignUp(String autoSignUp) {
        this.autoSignUp = autoSignUp;
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public WeixinProperties getWeixin() {
        return weixin;
    }

    public void setWeixin(WeixinProperties weixin) {
        this.weixin = weixin;
    }
}
