package com.chentongwei.security.core.validate.code;

import com.chentongwei.security.core.enums.ValidateCodeType;
import com.chentongwei.security.core.exception.ValidateCodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author chentongwei@bshf360.com 2018-03-27 13:15
 */
@Component
public class ValidateCodeProcessorHolder {

    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessors;

    public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeType type) {
        return findValidateCodeProcessor(type.toString().toLowerCase());
    }

    public ValidateCodeProcessor findValidateCodeProcessor(String type) {
        String name = type.toLowerCase() + ValidateCodeProcessor.class.getSimpleName();
        ValidateCodeProcessor validateCodeProcessor = validateCodeProcessors.get(name);
        if (null == validateCodeProcessor) {
            throw new ValidateCodeException("验证码处理器" + name + "不存在");
        }
        return validateCodeProcessor;
    }
}
