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

    /**
     * 验证图片验证码时，http请求中默认的携带图片验证码信息的参数的名称
     */
    String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "imageCode";

    /**
     * 验证码放入session时的前缀
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

    /**
     * 默认的处理验证码的url前缀
     */
    String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/code";
}
