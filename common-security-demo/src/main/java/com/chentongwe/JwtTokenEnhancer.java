/*
package com.chentongwe;

import com.chentongwe.security.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

*/
/**
 * @author chentongwei@bshf360.com 2018-05-10 10:12
 *//*

@Component("jwtTokenEnhancer")
public class JwtTokenEnhancer implements TokenEnhancer {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        Map<String, Object> infoMap = new HashMap();

        infoMap.put("user", myUserDetailsService.loadUserByUsername(authentication.getName()));

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(infoMap);
        return accessToken;
    }

}
*/
