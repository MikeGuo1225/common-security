package com.chentongwei.security.validate.enums;

/**
 * 验证码参数名称
 *
 * @author chentongwei@bshf360.com 2018-05-28 15:03
 */
public enum ValidateCodeParamNameEnum {

    /**
     * http请求中默认的携带图片验证码信息的参数的名称
     */
    DEFAULT_PARAMETER_NAME_CODE_IMAGE("imageCode"),

    /**
     * 验证极验证时，http请求中默认的携带极验证信息的参数的名称
     */
    DEFAULT_PARAMETER_NAME_CODE_GEETEST("geetestCode"),

    /**
     * 验证短信验证码时，http请求中默认的携带短信验证码信息的参数的名称
     */
    DEFAULT_PARAMETER_NAME_CODE_SMS("smsCode"),

    /**
     * 发送短信验证码 或 验证短信验证码时，传递手机号的参数的名称
     */
    DEFAULT_PARAMETER_NAME_MOBILE("mobile")
    ;

    private String value;

    ValidateCodeParamNameEnum(String imageCode) {
        this.value = imageCode;
    }

    public String value() {
        return value;
    }
}
