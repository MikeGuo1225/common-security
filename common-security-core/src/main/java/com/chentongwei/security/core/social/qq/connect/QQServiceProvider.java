package com.chentongwei.security.core.social.qq.connect;

import com.chentongwei.security.core.social.qq.api.QQ;
import com.chentongwei.security.core.social.qq.api.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * QQ服务提供商
 *
 * @author chentongwei@bshf360.com 2018-04-02 10:24
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

    private String appId;

    private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";

    private static final String URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";

    public QQServiceProvider(String appId, String appSecret) {
        super(new QQOAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
        this.appId = appId;
    }

    /**
     * 为什么每次都new一个对象，而不直接交给Spring管理，然后@Autowired注入？
     * 因为交给Spring管理后默认是单例bean，QQImpl的父类里有个全局变量accessToken，会出现线程安全问题。所以这里为每一个用户都new一个实例。
     *
     * @param accessToken：accessToken
     * @return
     */
    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(accessToken, appId);
    }
}
