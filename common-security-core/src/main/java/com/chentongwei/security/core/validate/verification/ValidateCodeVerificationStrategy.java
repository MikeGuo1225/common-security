package com.chentongwei.security.core.validate.verification;

import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码校验接口
 *
 * @author TongWei.Chen 2018-03-31 18:31:56
 */
public interface ValidateCodeVerificationStrategy {

    void verification(SessionStrategy sessionStrategy, ServletWebRequest request, String sessionKey);

}
