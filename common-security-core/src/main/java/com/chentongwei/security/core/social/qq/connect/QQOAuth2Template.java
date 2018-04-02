package com.chentongwei.security.core.social.qq.connect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * @author chentongwei@bshf360.com 2018-04-02 16:43
 */
public class QQOAuth2Template extends OAuth2Template {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public QQOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        setUseParametersForClientAuthentication(true);
    }
}
