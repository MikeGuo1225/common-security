package com.chentongwei.security.core.social.qq.connect;

import com.chentongwei.security.core.social.qq.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * QQ连接工厂
 *
 * @author chentongwei@bshf360.com 2018-04-02 11:13
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {

    public QQConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());
    }
}
