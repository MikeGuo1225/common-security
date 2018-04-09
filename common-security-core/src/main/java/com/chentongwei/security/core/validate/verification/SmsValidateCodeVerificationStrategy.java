package com.chentongwei.security.core.validate.verification;

import com.chentongwei.security.core.constant.SecurityConstant;
import com.chentongwei.security.core.enums.ValidateCodeType;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码校验接口
 *
 * @author TongWei.Chen 2018-03-31 18:31:56
 */
public class SmsValidateCodeVerificationStrategy implements ValidateCodeVerificationStrategy {

    @Override
    public void verification(ValidateCodeRepository validateCodeRepository, ServletWebRequest request, ValidateCodeType validateCodeType) {
        new CommonValidateCodeVerificationUtil().verifity(validateCodeRepository, request, validateCodeType, SecurityConstant.DEFAULT_PARAMETER_NAME_CODE_SMS);
    }
}
