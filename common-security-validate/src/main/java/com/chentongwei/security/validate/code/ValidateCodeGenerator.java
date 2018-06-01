package com.chentongwei.security.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码生成接口
 *
 * @author chentongwei@bshf360.com 2018-05-25 11:49
 */
public interface ValidateCodeGenerator {

    /**
     * 生成验证码
     *
     * @param request：请求
     * @return
     */
    ValidateCode generate(ServletWebRequest request);

}
