package com.chentongwei.security.validate.code;

import com.chentongwei.security.validate.enums.ValidateCodeTypeEnum;
import com.chentongwei.security.validate.exception.ValidateCodeException;
import com.chentongwei.security.validate.verification.ValidateCodeRepository;
import com.chentongwei.security.validate.verification.factory.ValidateCodeVerificationFactory;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * 抽象的验证码处理器
 *
 * @author chentongwei@bshf360.com 2018-05-28 14:55
 */
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 验证码处理器后缀
     */
    private static final String SUFFIX_CODE_PROCESSOR = "CodeProcessor";

    /**
     * 收集系统中所有的{@link ValidateCodeGenerator} 接口的实现类。
     */
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerators;

    /**
     * 验证码的存取删接口
     */
    @Autowired
    private ValidateCodeRepository validateCodeRepository;

    @Override
    public void create(ServletWebRequest request) throws Exception {
        /**
         * 生成验证码
         */
        C code = generate(request);
        /**
         * 保存验证码（session/redis）
         */
        save(request, code);
        /**
         * 发送验证码
         */
        send(request, code);
    }

    @Override
    public void validate(ServletWebRequest request) {
        ValidateCodeTypeEnum validateCodeType = getValidateCodeType();
        ValidateCodeVerificationFactory.getInstance().creator(validateCodeType.name())
                .verification(validateCodeRepository, request, validateCodeType);
        validateCodeRepository.remove(request, validateCodeType);
    }

    /**
     * 生成验证码
     *
     * @param request：请求
     * @return
     */
    private C generate(ServletWebRequest request) {
        // 获取image、sms、geetest
        String type = getValidateCodeType().toString().toLowerCase();
        // 获取验证码生成器名称，比如：imageValidateCodeGenerator
        String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
        logger.info("验证码生成器名称【{}】", generatorName);

        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
        if (null == validateCodeGenerator) {
            throw new ValidateCodeException("验证码生成器" + generatorName + "不存在！");
        }
        return (C) validateCodeGenerator.generate(request);
    }

    /**
     * 保存验证码到session/redis
     *
     * @param request：请求
     * @param code：验证码
     */
    private void save(ServletWebRequest request, C code) {
        validateCodeRepository.save(request, code, getValidateCodeType());
    }

    /**
     * 发送验证码
     * 具体实现有子类决定，因为图片验证码和sms或者geetest三者发送方式不同。
     *
     * @param request
     * @param code
     */
    protected abstract void send(ServletWebRequest request, C code) throws Exception;

    /**
     * 根据请求的Url获取验证码的类型
     *
     * @return：image、sms、geetest
     */
    private ValidateCodeTypeEnum getValidateCodeType() {
        String type = StringUtils.substringBefore(getClass().getSimpleName(), SUFFIX_CODE_PROCESSOR);
        return ValidateCodeTypeEnum.valueOf(type.toUpperCase());
    }

}
