package com.chentongwei.security.validate.verification.strategy;

import com.chentongwei.security.validate.code.geetest.GeetestCode;
import com.chentongwei.security.validate.enums.ValidateCodeTypeEnum;
import com.chentongwei.security.validate.exception.ValidateCodeException;
import com.chentongwei.security.validate.verification.ValidateCodeRepository;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码校验接口
 *
 * @author TongWei.Chen 2018-03-31 18:31:56
 */
public class GeetestValidateCodeVerificationStrategy implements ValidateCodeVerificationStrategy {

    @Override
    public void verification(ValidateCodeRepository validateCodeRepository, ServletWebRequest request, ValidateCodeTypeEnum validateCodeType) {
        GeetestCode geetestCodeInRepository = (GeetestCode) validateCodeRepository.get(request, validateCodeType);
        if (geetestCodeInRepository == null) {
            throw new ValidateCodeException("极验证验证码不存在，请刷新页面重试");
        }
        if (geetestCodeInRepository.isExpired()) {
            validateCodeRepository.remove(request, validateCodeType);
            throw new ValidateCodeException("极验证验证码已过期");
        }

        String geetestChallenge = request.getRequest().getParameter("geetest_challenge");
        String geetestValidate = request.getRequest().getParameter("geetest_validate");
        String geetestSeccode = request.getRequest().getParameter("geetest_seccode");

        //0 失败
        int gtStatus;
        if (1 == geetestCodeInRepository.getGtServerStatus()) {
            //gt-server正常，向gt-server进行二次验证
            gtStatus = geetestCodeInRepository.getGeetestLib().enhencedValidateRequest(
                    geetestChallenge, geetestValidate, geetestSeccode, geetestCodeInRepository.getUserid());
        } else {
            // gt-server非正常情况下，进行failback模式验证
            gtStatus = geetestCodeInRepository.getGeetestLib().failbackValidateRequest(geetestChallenge, geetestValidate, geetestSeccode);
        }
        if (1 != gtStatus) {
            validateCodeRepository.remove(request, validateCodeType);
            throw new ValidateCodeException("极验证验证错误");
        }
    }

}
