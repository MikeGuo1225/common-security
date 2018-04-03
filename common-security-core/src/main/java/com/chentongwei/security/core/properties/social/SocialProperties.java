package com.chentongwei.security.core.properties.social;

/**
 * @author chentongwei@bshf360.com 2018-04-02 11:55
 */
public class SocialProperties {

    /**
     * QQ登录URL
     */
    private String filterProcessesUrl = "/auth";

    private String tablePrefix;

    private QQProperties qq = new QQProperties();

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

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }
}
