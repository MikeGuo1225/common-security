package com.chentongwei.security.core.social;

/**
 * @author TongWei.Chen 2018-04-02 22:49:44
 */
public class SocialUserInfo {

    /**
     * 可以知道是QQ登录还是微信还是什么，前端有需要展示的话可以使用。
     */
    private String providerId;

    /**
     * openid
     */
    private String providerUserId;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String headimg;

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }
}
