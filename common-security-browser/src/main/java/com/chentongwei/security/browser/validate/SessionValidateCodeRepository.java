package com.chentongwei.security.browser.validate;

import com.chentongwei.security.core.constant.SecurityConstant;
import com.chentongwei.security.core.enums.ValidateCodeType;
import com.chentongwei.security.core.validate.code.ValidateCode;
import com.chentongwei.security.core.validate.verification.ValidateCodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * Session方式（适合不跨域的情况）
 *
 * @author TongWei.Chen 2018-04-09 20:14:03
 */
@Component
public class SessionValidateCodeRepository implements ValidateCodeRepository {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * session，存验证码
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Override
    public void save(ServletWebRequest request, ValidateCode validateCode, ValidateCodeType validateCodeType) {
        this.sessionStrategy.setAttribute(request, getSessionKey(validateCodeType), validateCode);
    }

    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType) {
        return (ValidateCode) this.sessionStrategy.getAttribute(request, getSessionKey(validateCodeType));
    }

    @Override
    public void remove(ServletWebRequest request, ValidateCodeType validateCodeType) {
        this.sessionStrategy.removeAttribute(request, getSessionKey(validateCodeType));
    }

    /**
     * 获取sessionKey
     *
     * @return
     */
    private String getSessionKey(ValidateCodeType validateCodeType) {
        logger.info("sessionKey：", SecurityConstant.SESSION_KEY_PREFIX + validateCodeType.toString().toUpperCase());
        return SecurityConstant.SESSION_KEY_PREFIX + validateCodeType.toString().toUpperCase();
    }
}
