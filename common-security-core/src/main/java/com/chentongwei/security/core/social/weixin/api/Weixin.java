package com.chentongwei.security.core.social.weixin.api;

/**
 * 微信API调用接口
 *
 * @author chentongwei@bshf360.com 2018-04-04 13:32
 */
public interface Weixin {

    WeixinUserInfo getUserInfo(String openId);

}
