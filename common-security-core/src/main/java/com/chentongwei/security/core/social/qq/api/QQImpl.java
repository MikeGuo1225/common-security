package com.chentongwei.security.core.social.qq.api;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

/**
 * @author chentongwei@bshf360.com 2018-04-02 09:55
 */
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

    private Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 获取OPENID的URL
     */
    private static final String GET_OPENID_URL = "https://graph.qq.com/oauth2.0/me?access_token=%s";

    /**
     * 获取用户基本信息的URL
     * 为什么这里不需要accessToken了？QQ互联官网说需要的。
     * 因为父类AbstractOAuth2ApiBinding已经为我们拼接好了，所以这里无需管了
     */
    private static final String GET_USERINFO_URL = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    /**
     * appId
     */
    private String appId;
    /**
     * openid
     */
    private String openId;

    public QQImpl(String accessToken, String appId) {
        /**
         * 为什么调用两个参数的构造器？
         * 因为一个参数的构造器采取的策略是：TokenStrategy.AUTHORIZATION_HEADER，是将accessToken放到header里，
         * QQ互联API需要直接放到url后面，普通参数传递的方式，所以我们这里指定TokenStrategy.ACCESS_TOKEN_PARAMETER
         */
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;

        /**
         * 获取openid
         * callback( {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"} );
         */
        String url = String.format(GET_OPENID_URL, accessToken);

        String result = getRestTemplate().getForObject(url, String.class);
        log.info(result);

        this.openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
    }

    @Override
    public QQUserInfo getUserInfo() {

        String url = String.format(GET_USERINFO_URL, appId, openId);
        String result = getRestTemplate().getForObject(url, String.class);
        log.info(result);

        QQUserInfo userInfo = JSON.parseObject(result, QQUserInfo.class);
        userInfo.setOpenId(this.openId);
        return userInfo;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
