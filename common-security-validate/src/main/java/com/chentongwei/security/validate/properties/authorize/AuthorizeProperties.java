package com.chentongwei.security.validate.properties.authorize;

/**
 * 权限认证的配置
 *
 * @author chentongwei@bshf360.com 2018-03-26 17:13
 */
public class AuthorizeProperties {

    /** 限制ip在一定时间内访问多少次数的url，若不写此url，则不会生效，也相当于开关 */
    private String ipValidateUrl;

    /** 限制ip在多少秒内能访问同一url多少次的秒数 */
    private int ipValidateSeconds = 3;

    /** 限制ip在多少秒内能访问同一url的次数 */
    private int ipValidateCount = 10;

    public String getIpValidateUrl() {
        return ipValidateUrl;
    }

    public void setIpValidateUrl(String ipValidateUrl) {
        this.ipValidateUrl = ipValidateUrl;
    }

    public int getIpValidateSeconds() {
        return ipValidateSeconds;
    }

    public void setIpValidateSeconds(int ipValidateSeconds) {
        this.ipValidateSeconds = ipValidateSeconds;
    }

    public int getIpValidateCount() {
        return ipValidateCount;
    }

    public void setIpValidateCount(int ipValidateCount) {
        this.ipValidateCount = ipValidateCount;
    }
}
