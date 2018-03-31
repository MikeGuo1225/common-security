package com.chentongwei.security.core.validate.verification;

import com.chentongwei.security.core.exception.ValidateCodeException;
import com.chentongwei.security.core.validate.geetest.GeetestCode;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码校验接口
 *
 * @author TongWei.Chen 2018-03-31 18:31:56
 */
public class GeetestValidateCodeVerificationStrategy implements ValidateCodeVerificationStrategy {

    @Override
    public void verification(SessionStrategy sessionStrategy, ServletWebRequest request, String sessionKey) {
        GeetestCode geetestCodeInSession = (GeetestCode) sessionStrategy.getAttribute(request, sessionKey);
        if (geetestCodeInSession == null) {
            throw new ValidateCodeException("极验证验证码不存在，请刷新页面重试");
        }
        if (geetestCodeInSession.isExpired()) {
            sessionStrategy.removeAttribute(request, sessionKey);
            throw new ValidateCodeException("极验证验证码已过期");
        }

        String geetestChallenge = request.getRequest().getParameter("geetest_challenge");
        String geetestValidate = request.getRequest().getParameter("geetest_validate");
        String geetestSeccode = request.getRequest().getParameter("geetest_seccode");

        //0 失败
        int gtStatus;
        if (1 == geetestCodeInSession.getGtServerStatus()) {
            //gt-server正常，向gt-server进行二次验证
            gtStatus = geetestCodeInSession.getGeetestLib().enhencedValidateRequest(
                    geetestChallenge, geetestValidate, geetestSeccode, geetestCodeInSession.getUserid());
        } else {
            // gt-server非正常情况下，进行failback模式验证
            gtStatus = geetestCodeInSession.getGeetestLib().failbackValidateRequest(geetestChallenge, geetestValidate, geetestSeccode);
        }
        if (1 != gtStatus) {
            sessionStrategy.removeAttribute(request, sessionKey);
            throw new ValidateCodeException("极验证验证错误");
        }
    }

}
