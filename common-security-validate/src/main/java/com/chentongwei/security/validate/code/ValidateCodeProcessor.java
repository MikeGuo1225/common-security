package com.chentongwei.security.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码处理器接口
 *
 * @author chentongwei@bshf360.com 2018-05-28 14:53
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
     * 校验验证码
     *
     * @param request：请求
     */
    void validate(ServletWebRequest request);
}