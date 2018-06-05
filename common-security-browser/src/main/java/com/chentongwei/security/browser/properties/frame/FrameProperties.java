package com.chentongwei.security.browser.properties.frame;

/**
 * 关于Iframe嵌套的一些配置
 *
 * @author chentongwei@bshf360.com 2018-06-05 16:12
 */
public class FrameProperties {

    /** 0:不允许开启frame；1：允许开启iframe */
    private int disableStatus = 0;

    public int getDisableStatus() {
        return disableStatus;
    }

    public void setDisableStatus(int disableStatus) {
        this.disableStatus = disableStatus;
    }
}
