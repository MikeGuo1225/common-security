package com.chentongwei.security.validate.code;

import com.chentongwei.security.validate.enums.ValidateCodeTypeEnum;
import com.chentongwei.security.validate.exception.ValidateCodeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author chentongwei@bshf360.com 2018-05-28 16:09
 */
@Component
public class ValidateCodeProcessorHolder {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessor;

    /**
     * 查找验证码处理器
     *
     * @param type：验证码类型
     * @return
     */
    public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeTypeEnum type) {
        return findValidateCodeProcessor(type.toString().toLowerCase());
    }

    /**
     * 查找验证码处理器
     *
     * validateCodeProcessor ==> {@link com.chentongwei.security.validate.code.image.ImageCodeProcessor}
     *
     * @param type：验证码类型
     * @return
     */
    public ValidateCodeProcessor findValidateCodeProcessor(String type) {
        String name = type.toLowerCase() + ValidateCodeProcessor.class.getSimpleName();
        ValidateCodeProcessor validateCodeProcessor = this.validateCodeProcessor.get(name);
        logger.info("验证码处理器【{}】", name);

        if (null == validateCodeProcessor) {
            throw new ValidateCodeException("验证码处理器" + name + "不存在");
        }
        return validateCodeProcessor;
    }

}
