package com.chentongwei.security.core.properties.code;

/**
 * 图片验证码基础配置
 *
 * @author chentongwei@bshf360.com 2018-03-27 12:57
 */
public class ImageCodeProperties extends CommonCodeProperties {

    public ImageCodeProperties() {
        super.setLength(4);
    }

    private int width = 67;
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
