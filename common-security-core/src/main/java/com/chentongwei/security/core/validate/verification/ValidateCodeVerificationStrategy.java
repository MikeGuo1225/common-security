package com.chentongwei.security.core.validate.verification;

import com.chentongwei.security.core.enums.ValidateCodeType;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码校验接口
 *
 * @author TongWei.Chen 2018-03-31 18:31:56
 */
public interface ValidateCodeVerificationStrategy {

    void verification(ValidateCodeRepository validateCodeRepository, ServletWebRequest request, ValidateCodeType validateCodeType);

}
