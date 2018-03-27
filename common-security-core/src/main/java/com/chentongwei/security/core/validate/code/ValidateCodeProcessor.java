package com.chentongwei.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码处理器，封装不同校验码的处理逻辑
 *
 * @author chentongwei@bshf360.com 2018-03-27 11:24
 */
public interface ValidateCodeProcessor {

    /**
     * 创建验证码
     *
     * @param request：请求
     * @throws Exception
     */
    void create(ServletWebRequest request) throws Exception;

    /**
     * 验证验证码
     *
     * param request：请求
     */
    void validate(ServletWebRequest request);

}
