package com.chentongwei.security.validate.code.geetest;

import com.alibaba.fastjson.JSON;
import com.chentongwei.security.validate.code.ValidateCode;
import com.chentongwei.security.validate.code.ValidateCodeGenerator;
import com.chentongwei.security.validate.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.UUID;

/**
 * 极验证生成器
 *
 * @author TongWei.Chen 2018-03-30 22:47:00
 */
public class GeetestCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public ValidateCode generate(ServletWebRequest request) {
        GeetestLib gtSdk = new GeetestLib(securityProperties.getCode().getGeetest().getId(),
                securityProperties.getCode().getGeetest().getKey(),
                securityProperties.getCode().getGeetest().getNewfailback());
        String resStr;
        String userid = UUID.randomUUID().toString();
        //进行验证预处理
        int gtServerStatus = gtSdk.preProcess(userid);

        resStr = gtSdk.getResponseStr();
        GeetestCode geetestCode = JSON.parseObject(resStr, GeetestCode.class);
        geetestCode.plusExpireIn(securityProperties.getCode().getGeetest().getExpireIn())
                    .userid(userid).gtServerStatus(gtServerStatus).geetestLib(gtSdk);
        return geetestCode;
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
