package com.chentongwei.security.validate.code.geetest;


import com.chentongwei.security.validate.code.ValidateCode;

import java.time.LocalDateTime;

/**
 * 极验证
 *
 * @author TongWei.Chen 2018-03-30 22:46:27
 */
public class GeetestCode extends ValidateCode {

    private static final long serialVersionUID = 746373274054964636L;
    /**
     * 验证码信息
     */
    private String challenge;
    private String gt;
    private String success;

    /**
     * 校验信息；userid，gtServerStatus
     */
    private String userid;
    private int gtServerStatus;

    private GeetestLib geetestLib;

    public GeetestCode() {
    }

    public GeetestCode(String code, int expireIn) {
        super(code, expireIn);
    }

    public GeetestCode plusExpireIn(int expireIn) {
        super.setExpireTime(LocalDateTime.now().plusSeconds(expireIn));
        return this;
    }

    public GeetestCode userid(String userid) {
        this.userid = userid;
        return this;
    }

    public GeetestCode gtServerStatus(int gtServerStatus) {
        this.gtServerStatus = gtServerStatus;
        return this;
    }

    public GeetestCode geetestLib(GeetestLib geetestLib) {
        this.geetestLib = geetestLib;
        return this;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public String getGt() {
        return gt;
    }

    public void setGt(String gt) {
        this.gt = gt;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int getGtServerStatus() {
        return gtServerStatus;
    }

    public void setGtServerStatus(int gtServerStatus) {
        this.gtServerStatus = gtServerStatus;
    }

    public GeetestLib getGeetestLib() {
        return geetestLib;
    }

    public void setGeetestLib(GeetestLib geetestLib) {
        this.geetestLib = geetestLib;
    }
}
