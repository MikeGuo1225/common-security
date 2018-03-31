package com.chentongwei.security.core.validate.verification;

import com.chentongwei.security.core.constant.SecurityConstant;
import com.chentongwei.security.core.exception.ValidateCodeException;
import com.chentongwei.security.core.validate.code.ValidateCode;
import com.chentongwei.security.core.validate.geetest.GeetestCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码校验接口
 *
 * @author TongWei.Chen 2018-03-31 18:31:56
 */
public class ImageValidateCodeVerificationStrategy implements ValidateCodeVerificationStrategy {

    @Override
    public void verification(SessionStrategy sessionStrategy, ServletWebRequest request, String sessionKey) {
        new CommonValidateCodeVerificationUtil().verifity(sessionStrategy, request, sessionKey, SecurityConstant.DEFAULT_PARAMETER_NAME_CODE_IMAGE);
    }
}
