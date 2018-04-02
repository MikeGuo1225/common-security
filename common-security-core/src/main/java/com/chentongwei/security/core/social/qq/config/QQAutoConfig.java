package com.chentongwei.security.core.social.qq.config;

import com.alibaba.fastjson.JSON;
import com.chentongwei.security.core.properties.SecurityProperties;
import com.chentongwei.security.core.properties.social.QQProperties;
import com.chentongwei.security.core.social.qq.connect.QQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

/**
 * @author chentongwei@bshf360.com 2018-04-02 11:58
 */
@Configuration
/**
 * 只有当配置文件配置了com.chentongwei.security.social.qq.app-id并且有值后，才会加载此类，否则不会加载此类的任何方法
 */
//@ConditionalOnProperty(prefix = "com.chentongwei.security.social.qq", name = "app-id")
public class QQAutoConfig extends SocialAutoConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        QQProperties qqConfig = securityProperties.getSocial().getQq();
        System.out.println("qqConfig....." + JSON.toJSONString(qqConfig));
        return new QQConnectionFactory(qqConfig.getProviderId(), qqConfig.getAppId(), qqConfig.getAppSecret());
    }
}

