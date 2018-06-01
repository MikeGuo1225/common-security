package com.chentongwei.security.validate.code.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 默认的短信发送接口实现类
 *
 * @author chentongwei@bshf360.com 2018-03-28 09:52
 */
public class DefaultSmsCodeSender implements SmsCodeSender {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void send(String mobile, String code) {
        logger.info("向手机【{}】发送短信验证码【{}】", mobile, code);
    }
}
