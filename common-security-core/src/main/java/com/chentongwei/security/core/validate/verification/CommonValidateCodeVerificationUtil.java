package com.chentongwei.security.core.validate.verification;

import com.chentongwei.security.core.constant.SecurityConstant;
import com.chentongwei.security.core.exception.ValidateCodeException;
import com.chentongwei.security.core.validate.code.ValidateCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 通用的验证类
 *
 * @author TongWei.Chen 2018-03-31 19:00:31
 */
public class CommonValidateCodeVerificationUtil {

    public void verifity(SessionStrategy sessionStrategy, ServletWebRequest request, String sessionKey, String codeParam) {
        ValidateCode codeInSession = (ValidateCode) sessionStrategy.getAttribute(request, sessionKey);
        String codeInRequest;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), codeParam);
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException("获取验证码的值失败！");
        }
        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("验证码的值不能为空");
        }

        if (codeInSession == null) {
            throw new ValidateCodeException("验证码不存在，请刷新页面重试");
        }

        if (codeInSession.isExpired()) {
            sessionStrategy.removeAttribute(request, sessionKey);
            throw new ValidateCodeException("验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            sessionStrategy.removeAttribute(request, sessionKey);
            throw new ValidateCodeException("验证码不匹配");
        }
    }

}
