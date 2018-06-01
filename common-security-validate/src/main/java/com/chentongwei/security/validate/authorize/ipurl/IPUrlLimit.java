package com.chentongwei.security.validate.authorize.ipurl;

import java.time.LocalDateTime;

/**
 * IP+URL的限制，比如每秒访问最多5次，否则出现验证码
 *
 * @author chentongwei@bshf360.com 2018-03-28 17:47
 */
public class IPUrlLimit {

    /**
     * 次数
     */
    private int count;

    /**
     * 时间，单位：秒(s)
     */
    private LocalDateTime localDateTime;

    public IPUrlLimit(int expireIn) {
        this(1, expireIn);
    }

    public IPUrlLimit(int count, int expireIn) {
        this.count = count;
        this.localDateTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public IPUrlLimit count(int count) {
        setCount(count);
        return this;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.localDateTime);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = getCount() + count;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
