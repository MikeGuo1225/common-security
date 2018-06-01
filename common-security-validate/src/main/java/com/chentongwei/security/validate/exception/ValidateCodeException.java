package com.chentongwei.security.validate.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码自定义异常
 *
 * @author chentongwei@bshf360.com 2018-03-27 12:36
 */
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
