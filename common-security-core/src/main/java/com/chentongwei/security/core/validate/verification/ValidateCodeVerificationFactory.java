package com.chentongwei.security.core.validate.verification;

import com.chentongwei.security.core.enums.ValidateCodeType;

import java.util.HashMap;
import java.util.Map;

/**
 * 验证码校验工厂
 *
 * @author TongWei.Chen 2018-03-31 18:33:35
 */
public final class ValidateCodeVerificationFactory {

    private ValidateCodeVerificationFactory() {}

    private static class InnterValidateCodeVerification {
       private static final ValidateCodeVerificationFactory INSTANCE = new ValidateCodeVerificationFactory();
    }

    private static Map<String, ValidateCodeVerificationStrategy> maps = new HashMap();

    static {
        maps.put(ValidateCodeType.GEETEST.name(), new GeetestValidateCodeVerificationStrategy());
        maps.put(ValidateCodeType.IMAGE.name(), new ImageValidateCodeVerificationStrategy());
        maps.put(ValidateCodeType.SMS.name(), new SmsValidateCodeVerificationStrategy());
    }

    public final ValidateCodeVerificationStrategy creator(String key) {
        return maps.get(key);
    }

    public static ValidateCodeVerificationFactory getInstance() {
        return InnterValidateCodeVerification.INSTANCE;
    }
}
