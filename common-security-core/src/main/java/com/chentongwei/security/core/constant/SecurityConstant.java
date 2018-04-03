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
     * 默认注册页
     */
    String DEFAULT_REGISTER_PAGE_URL = "/default-regist.html";

    /**
     * 当访问需要身份认证的接口时，需要跳转到的URL
     */
    String DEFAULT_UNAUTHENTICATION_URL = "/authentication/require";

    /**
     * 默认的登录接口
     */
    String DEFAULT_LOGIN_PROCESSING_URL_FORM = "/login";

    /**
     * 默认的手机验证码登录请求处理url
     */
    String DEFAULT_LOGIN_PROCESSING_URL_MOBILE = "/mobile";

    /**
     * 验证图片验证码时，http请求中默认的携带图片验证码信息的参数的名称
     */
    String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "imageCode";

    /**
     * 验证短信验证码时，http请求中默认的携带短信验证码信息的参数的名称
     */
    String DEFAULT_PARAMETER_NAME_CODE_SMS = "smsCode";

    /**
     * 验证极验证时，http请求中默认的携带极验证信息的参数的名称
     */
    String DEFAULT_PARAMETER_NAME_CODE_GEETEST = "geetestCode";

    /**
     * 发送短信验证码 或 验证短信验证码时，传递手机号的参数的名称
     */
    String DEFAULT_PARAMETER_NAME_MOBILE = "mobile";

    /**
     * 验证码放入session时的前缀
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

    /**
     * 默认的处理验证码的url前缀
     */
    String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/code";

    /**
     * 获取QQ登录后用户信息接口，此接口主要用于展示到注册页面一些基本信息，比如头像和昵称等
     */
    String DEFAULT_GET_SOCIAL_USER_INFO = "/social/user";
}
