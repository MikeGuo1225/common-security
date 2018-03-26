package com.chentongwei.security.core.constant;

/**
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
}
