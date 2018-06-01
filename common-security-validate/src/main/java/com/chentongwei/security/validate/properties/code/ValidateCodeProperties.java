package com.chentongwei.security.validate.properties.code;

/**
 * 验证码总的配置
 *
 * @author chentongwei@bshf360.com 2018-05-25 11:54
 */
public class ValidateCodeProperties {

    /** 图形验证码配置 */
    private ImageCodeProperties image = new ImageCodeProperties();

    /** 短信验证码配置 */
    private SmsCodeProperties sms = new SmsCodeProperties();

    /** 极验证配置 */
    private GeetestCodeProperties geetest = new GeetestCodeProperties();

    public ImageCodeProperties getImage() {
        return image;
    }

    public void setImage(ImageCodeProperties image) {
        this.image = image;
    }

    public SmsCodeProperties getSms() {
        return sms;
    }

    public void setSms(SmsCodeProperties sms) {
        this.sms = sms;
    }

    public GeetestCodeProperties getGeetest() {
        return geetest;
    }

    public void setGeetest(GeetestCodeProperties geetest) {
        this.geetest = geetest;
    }
}
