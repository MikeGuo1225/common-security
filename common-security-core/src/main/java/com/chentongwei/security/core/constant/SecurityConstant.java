package com.chentongwei.security.core.constant;

/**
 * 常量
 *
 * @author chentongwei@bshf360.com 2018-03-26 11:40
 */
public interface SecurityConstant {

    /**
     * 默认登录页
     */
    String DEFAULT_LOGIN_PAGE_URL = "/default-login.html";

    /**
     * 当访问需要身份认证的接口时，需要跳转到的URL
     */
    String DEFAULT_UNAUTHENTICATION_URL = "/authentication/require";

    /**
     * 默认的登录接口
     */
    String DEFAULT_LOGIN_PROCESSING_URL_FORM = "/login";
}
