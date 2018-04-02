package com.chentongwei.security.core.enums;

/**
 * 是否开启iframe引入。默认禁用。
 * 1：不允许（缺省值）；0：允许
 *
 * @author chentongwei@bshf360.com 2018-04-02 14:11
 */
public enum FrameDisableStatus {
    /**
     * 不允许开启frame
     */
    FORBIDDEN(1),
    /**
     * 允许开启frame
     */
    ALLOW(0)
    ;

    private int status;

    FrameDisableStatus(int status) {
        this.status = status;
    }

    public int status() {
        return status;
    }
}
