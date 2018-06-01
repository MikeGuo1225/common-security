package com.chentongwei.security.validate.verification.strategy;

import com.chentongwei.security.validate.enums.ValidateCodeParamNameEnum;
import com.chentongwei.security.validate.enums.ValidateCodeTypeEnum;
import com.chentongwei.security.validate.verification.ValidateCodeRepository;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码校验接口
 *
 * @author TongWei.Chen 2018-03-31 18:31:56
 */
public class SmsValidateCodeVerificationStrategy implements ValidateCodeVerificationStrategy {

    @Override
    public void verification(ValidateCodeRepository validateCodeRepository, ServletWebRequest request, ValidateCodeTypeEnum validateCodeType) {
        new CommonValidateCodeVerification().verifity(validateCodeRepository, request, validateCodeType, ValidateCodeParamNameEnum.DEFAULT_PARAMETER_NAME_CODE_SMS.value());
    }
}
