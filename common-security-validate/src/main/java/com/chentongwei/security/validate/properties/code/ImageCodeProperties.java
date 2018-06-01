package com.chentongwei.security.validate.properties.code;

/**
 * @author chentongwei@bshf360.com 2018-05-25 11:55
 */
public class ImageCodeProperties extends CommonCodeProperties {

    /** 图形验证码默认长度为4 */
    public ImageCodeProperties() {
        super.setLength(4);
    }

    /** 图形验证码默认宽度为67 */
    private int width = 67;

    /** 图形验证码默认高度为23 */
    private int height = 23;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
