package com.chentongwei.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码生成接口
 *
 * @author chentongwei@bshf360.com 2018-03-27 12:24
 */
public interface ValidateCodeGenerator {

    /**
     * 验证码生成接口
     */
    ValidateCode generate(ServletWebRequest request);
}
