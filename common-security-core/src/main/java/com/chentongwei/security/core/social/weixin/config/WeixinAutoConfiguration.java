package com.chentongwei.security.core.social.weixin.config;

import com.chentongwei.security.core.properties.SecurityProperties;
import com.chentongwei.security.core.social.CtwConnectView;
import com.chentongwei.security.core.social.weixin.connect.WeixinConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.web.servlet.View;

/**
 * 微信登录配置
 *
 * @author chentongwei@bshf360.com 2018-04-04 14:16
 */
@Configuration
@ConditionalOnProperty(name = "com.chentongwei.security.social.weixin.used", havingValue = "true")
public class WeixinAutoConfiguration extends SocialAutoConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        return new WeixinConnectionFactory(
                securityProperties.getSocial().getWeixin().getProviderId(),
                securityProperties.getSocial().getWeixin().getAppId(),
                securityProperties.getSocial().getWeixin().getAppSecret()
        );
    }

    @Bean({"connect/weixintestConnect", "connect/weixintestConnected"})
    @ConditionalOnMissingBean(name = "weixinConnect")
    public View weixinConnectedView() {
        return new CtwConnectView();
    }
}
