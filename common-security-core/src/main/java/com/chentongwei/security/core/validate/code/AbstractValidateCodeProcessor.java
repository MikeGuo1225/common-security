package com.chentongwei.security.core.validate.code;

import com.chentongwei.security.core.enums.ValidateCodeType;
import com.chentongwei.security.core.exception.ValidateCodeException;
import com.chentongwei.security.core.validate.verification.ValidateCodeRepository;
import com.chentongwei.security.core.validate.verification.ValidateCodeVerificationFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * @author chentongwei@bshf360.com 2018-03-27 11:34
 */
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {

    @Autowired
    private ValidateCodeRepository validateCodeRepository;

    /**
     * 收集系统中所有的 {@link ValidateCodeGenerator} 接口的实现。
     */
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerators;

    @Override
    public void create(ServletWebRequest request) throws Exception {
        /**
         * 生成验证码
         */
        C validateCode = generate(request);
        /**
         * 保存到session
         */
        save(request, validateCode);

        /**
         * 发送验证码
         */
        send(request, validateCode);
    }

    @Override
    public void validate(ServletWebRequest request) {
        ValidateCodeType validateCodeType = getValidateCodeType();
        ValidateCodeVerificationFactory.getInstance().creator(validateCodeType.name())
                .verification(validateCodeRepository, request, validateCodeType);
        validateCodeRepository.remove(request, validateCodeType);
    }

    /**
     * 发送验证码，由子类具体实现
     *
     * @param request：请求
     * @param validateCode：验证码
     */
    protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;

    /**
     * 生成验证码
     *
     * @param request：请求
     * @return
     */
    private C generate(ServletWebRequest request) {
        String type = getValidateCodeType().toString().toLowerCase();
        String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
        if (null == validateCodeGenerator) {
            throw new ValidateCodeException("验证码生成器" + generatorName + "不存在！");
        }
        return (C) validateCodeGenerator.generate(request);
    }

    /**
     * 保存验证码
     *
     * @param request：请求
     * @param validateCode：验证码
     */
    private void save(ServletWebRequest request, C validateCode) {
        validateCodeRepository.save(request, validateCode, getValidateCodeType());
    }

    /**
     * 根据请求的Url获取验证码的类型
     *
     * @return
     */
    private ValidateCodeType getValidateCodeType() {
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
        return ValidateCodeType.valueOf(type.toUpperCase());
    }
}
