package com.chentongwei.security.validate.verification.factory;

import com.chentongwei.security.validate.enums.ValidateCodeTypeEnum;
import com.chentongwei.security.validate.verification.strategy.GeetestValidateCodeVerificationStrategy;
import com.chentongwei.security.validate.verification.strategy.ImageValidateCodeVerificationStrategy;
import com.chentongwei.security.validate.verification.strategy.SmsValidateCodeVerificationStrategy;
import com.chentongwei.security.validate.verification.strategy.ValidateCodeVerificationStrategy;

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
        maps.put(ValidateCodeTypeEnum.IMAGE.name(), new ImageValidateCodeVerificationStrategy());
        maps.put(ValidateCodeTypeEnum.SMS.name(), new SmsValidateCodeVerificationStrategy());
        maps.put(ValidateCodeTypeEnum.GEETEST.name(), new GeetestValidateCodeVerificationStrategy());
    }

    public final ValidateCodeVerificationStrategy creator(String key) {
        return maps.get(key);
    }

    public static ValidateCodeVerificationFactory getInstance() {
        return InnterValidateCodeVerification.INSTANCE;
    }
}
