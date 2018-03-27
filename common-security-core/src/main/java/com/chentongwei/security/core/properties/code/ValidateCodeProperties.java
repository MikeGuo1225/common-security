package com.chentongwei.security.core.properties.code;

/**
 * 验证码基础配置
 *
 * @author chentongwei@bshf360.com 2018-03-27 12:59
 */
public class ValidateCodeProperties {

    private ImageCodeProperties image = new ImageCodeProperties();

    public ImageCodeProperties getImage() {
        return image;
    }

    public void setImage(ImageCodeProperties image) {
        this.image = image;
    }
}
