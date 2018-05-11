package com.chentongwei.security.app.jwt;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义jwt返回值
 *
 * @author chentongwei@bshf360.com 2018-05-09 15:18
 */
public class CtwTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        Map<String, Object> infoMap = new HashMap();
        infoMap.put("jianjie", "什么都要会一点，这样装起逼来不会尴尬！");

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(infoMap);

        return accessToken;
    }
}
