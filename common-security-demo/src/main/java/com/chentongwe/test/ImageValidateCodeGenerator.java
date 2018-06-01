package com.chentongwe.test;

import com.chentongwei.security.validate.code.ValidateCode;
import com.chentongwei.security.validate.code.ValidateCodeGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author chentongwei@bshf360.com 2018-06-01 18:09
 */
//@Component("imageValidateCodeGenerator")
public class ImageValidateCodeGenerator implements ValidateCodeGenerator  {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public ValidateCode generate(ServletWebRequest request) {
        logger.info("自定义的验证码生成逻辑....");
        return new ValidateCode();
    }
}
