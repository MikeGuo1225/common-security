package com.chentongwei.security.validate.code.sms;

/**
 * 短信发送接口
 *
 * @author chentongwei@bshf360.com 2018-03-28 09:51
 */
public interface SmsCodeSender {

    /**
     * 短信发送
     *
     * @param mobile：手机号
     * @param code：验证码
     */
    void send(String mobile, String code);

}
