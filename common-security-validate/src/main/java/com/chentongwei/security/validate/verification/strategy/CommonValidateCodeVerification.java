package com.chentongwei.security.validate.verification.strategy;

import com.chentongwei.security.validate.code.ValidateCode;
import com.chentongwei.security.validate.enums.ValidateCodeTypeEnum;
import com.chentongwei.security.validate.exception.ValidateCodeException;
import com.chentongwei.security.validate.verification.ValidateCodeRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 通用的校验逻辑，图形验证码/sms验证码公用
 *
 * @author chentongwei@bshf360.com 2018-05-28 15:51
 */
public class CommonValidateCodeVerification {

    /**
     * 通用的校验逻辑，图形验证码/sms验证码公用
     *
     * @param validateCodeRepository：验证码存取删接口
     * @param request：请求
     * @param validateCodeType：验证码类型
     * @param codeParam：验证码参数
     */
    public void verifity(ValidateCodeRepository validateCodeRepository, ServletWebRequest request, ValidateCodeTypeEnum validateCodeType, String codeParam) {
        ValidateCode code = validateCodeRepository.get(request, validateCodeType);
        String codeInRequest;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), codeParam);
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException("获取验证码的值失败！");
        }
        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("验证码的值不能为空");
        }

        if (code == null) {
            throw new ValidateCodeException("验证码不存在，请刷新页面重试");
        }

        if (code.isExpired()) {
            validateCodeRepository.remove(request, validateCodeType);
            throw new ValidateCodeException("验证码已过期");
        }

        if (!StringUtils.equals(code.getCode(), codeInRequest)) {
            validateCodeRepository.remove(request, validateCodeType);
            throw new ValidateCodeException("验证码不匹配");
        }
    }

}
