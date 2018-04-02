package com.chentongwei.security.core.social.qq.connect;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/**
 * 此类作用：
 * 流程分析：
 * OAuth2AuthenticationService.getAuthToken()
 * ==>
 * OAuth2Template.exchangeForAccess()
 * ==>
 * OAuth2Template.postForAccessGrant()
 * return this.extractAccessGrant((Map)this.getRestTemplate().postForObject(accessTokenUrl, parameters, Map.class, new Object[0]));
 * Map.class暴露出他期望返回一个json格式的数据
 *
 * 但是QQ接口返回的是text/html
 * 所以会进入到
 * OAuth2AuthenticationService.getAuthToken()的catch块
 * 下一步
 * SocialAuthenticationFilter.attemptAuthService==>auth == null return null; ==>throw new AuthenticationServiceException("authentication failed");
 * 进入失败处理器
 *
 * 问题产生原因就是因为找不到text/html的响应格式，
 * 源码可以看
 * OAuth2Template.createRestTemplate()方法
 * converters.add(new FormHttpMessageConverter());
 * converters.add(new FormMapHttpMessageConverter());
 * converters.add(new MappingJackson2HttpMessageConverter());
 * 已经加了三个，两个form一个json，并没有text/html
 * 所以我们需要定义自己的类并继承OAuth2Template来补充上text/html
 *
 * @author chentongwei@bshf360.com 2018-04-02 16:43
 */
public class QQOAuth2Template extends OAuth2Template {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public QQOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        /**
         * 这样发请求的时候才会带上appId和appSecret
         *
         * 详情请看OAuth2Template.exchangeForAccess()
         */
        setUseParametersForClientAuthentication(true);
    }

    /**
     * 按照QQ标准做自定义解析
     * 父类extractAccessGrant方法源码是按照json格式解析的，所以这里要重写此方法，
     * 按照qq返回的格式进行自定义解析
     *
     * @param accessTokenUrl：获取token的url
     * @param parameters:参数
     * @return
     */
    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        String result = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
        logger.info("获取accessToken为：" + result);
        String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(result, "&");

        String accessToken = StringUtils.substringAfterLast(items[0], "=");
        Long expiresIn = new Long(StringUtils.substringAfterLast(items[1], "="));
        String refreshToken = StringUtils.substringAfterLast(items[2], "=");
        return new AccessGrant(accessToken, null, refreshToken, expiresIn);
    }

    /**
     * 将text/html加到响应里，否则无法解析text/html格式，导致报错。
     *
     * @return
     */
    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }
}
