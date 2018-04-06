package com.chentongwei.security.core.validate.code;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 验证码实体
 *
 * @author chentongwei@bshf360.com 2018-03-27 11:35
 */
public class ValidateCode implements Serializable {

    private static final long serialVersionUID = 6892345317726554972L;
    private String code;

    private LocalDateTime expireTime;

    public ValidateCode() {
    }

    public ValidateCode(String code, int expireIn) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public ValidateCode(String code, LocalDateTime expireTime) {
        this.code = code;
        this.expireTime = expireTime;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireTime);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }
}
