package com.chentongwei.security.core.constant;

/**
 * 核心包的配置
 *
 * @author chentongwei@bshf360.com 2018-06-01 10:59
 */
public interface CoreConstants {

    /**
     * 当访问需要身份认证的接口时，需要跳转到的URL
     */
    String DEFAULT_UNAUTHENTICATION_URL = "/authentication/require";
}
