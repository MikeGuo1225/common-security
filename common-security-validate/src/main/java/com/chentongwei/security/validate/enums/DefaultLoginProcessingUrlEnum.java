package com.chentongwei.security.validate.enums;

/**
 * 默认的登录接口，目前有login和mobile
 *
 * @author chentongwei@bshf360.com 2018-06-01 12:32
 */
public enum DefaultLoginProcessingUrlEnum {

    /**
     * 默认的登录接口
     */
    FORM("/login"),

    /**
     * 默认的手机验证码登录请求处理url
     */
    MOBILE("/mobile")
    ;

    private String url;

    DefaultLoginProcessingUrlEnum(String url) {
        this.url = url;
    }

    public String url() {
        return url;
    }
}
