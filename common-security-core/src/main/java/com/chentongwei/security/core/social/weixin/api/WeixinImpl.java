package com.chentongwei.security.core.social.weixin.api;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.nio.charset.Charset;
import java.util.List;

/**
 * 微信API调用模板，socpe为Request的Spring Bean，根据当前用户的accessToken创建。
 *
 * @author chentongwei@bshf360.com 2018-04-04 13:33
 */
public class WeixinImpl extends AbstractOAuth2ApiBinding implements Weixin {

    /**
     * 获取用户信息的url，同QQ一样，accessToken由父类传递，所以这里无需accessToken
     */
    private static final String GET_USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo?openid=";

    public WeixinImpl(String accessToken) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
    }

    /**
     * 默认注册的StringHttpMessageConverter字符集为ISO-8859-1，但是微信返回的是UTF-8的，所以会乱码，所以
     * 此处覆盖原有的方法，将原来的StringHttpMessageConverter删除，重新add一个进去。
     *
     * 注意：
     * QQ默认是没有StringHttpMessageConverter的，所以我们直接add了一个。而微信有，但是编码不符合我们要求。
     *
     * @return
     */
    @Override
    protected List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> messageConverters = super.getMessageConverters();
        messageConverters.remove(0);
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return messageConverters;
    }

    /**
     * 获取微信用户信息
     *
     * @param openId：openid
     * @return
     */
    @Override
    public WeixinUserInfo getUserInfo(String openId) {
        String url = GET_USER_INFO_URL + openId;
        String response = getRestTemplate().getForObject(url, String.class);
        if (StringUtils.contains(response, "errcode")) {
            return null;
        }
        WeixinUserInfo userInfo = JSON.parseObject(response, WeixinUserInfo.class);
        return userInfo;

    }
}
