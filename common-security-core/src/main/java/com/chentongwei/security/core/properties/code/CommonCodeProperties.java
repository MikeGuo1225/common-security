package com.chentongwei.security.core.properties.code;

/**
 * 通用的验证码配置
 *
 * @author chentongwei@bshf360.com 2018-03-28 09:58
 */
public class CommonCodeProperties {

    /**
     * 默认验证码长度为6
     */
    private int length = 6;
    /**
     * 默认有效期1分钟
     */
    private int expireIn = 60;
    /**
     * 需要验证码的url
     */
    private String url;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(int expireIn) {
        this.expireIn = expireIn;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
