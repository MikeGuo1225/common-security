package com.chentongwei.security.core.validate.verification;

import com.chentongwei.security.core.enums.ValidateCodeType;
import com.chentongwei.security.core.exception.ValidateCodeException;
import com.chentongwei.security.core.validate.code.ValidateCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 通用的验证类
 *
 * @author TongWei.Chen 2018-03-31 19:00:31
 */
public class CommonValidateCodeVerificationUtil {

    public void verifity(ValidateCodeRepository validateCodeRepository, ServletWebRequest request, ValidateCodeType validateCodeType, String codeParam) {
        ValidateCode geetestCodeInRepository = validateCodeRepository.get(request, validateCodeType);
        String codeInRequest;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), codeParam);
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException("获取验证码的值失败！");
        }
        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("验证码的值不能为空");
        }

        if (geetestCodeInRepository == null) {
            throw new ValidateCodeException("验证码不存在，请刷新页面重试");
        }

        if (geetestCodeInRepository.isExpired()) {
            validateCodeRepository.remove(request, validateCodeType);
            throw new ValidateCodeException("验证码已过期");
        }

        if (!StringUtils.equals(geetestCodeInRepository.getCode(), codeInRequest)) {
            validateCodeRepository.remove(request, validateCodeType);
            throw new ValidateCodeException("验证码不匹配");
        }
    }

}
