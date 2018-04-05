package com.chentongwei.security.core.social.qq.config;

import com.chentongwei.security.core.properties.SecurityProperties;
import com.chentongwei.security.core.properties.social.QQProperties;
import com.chentongwei.security.core.social.CtwConnectView;
import com.chentongwei.security.core.social.qq.connect.QQConnectionFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.servlet.View;

import javax.sql.DataSource;

/**
 * @author chentongwei@bshf360.com 2018-04-02 11:58
 */
@Configuration
/**
 * 只有当配置文件配置了com.chentongwei.security.social.qq.used=true后，才会加载此类，否则不会加载此类的任何方法
 */
@ConditionalOnProperty(name = "com.chentongwei.security.social.qq.used", havingValue = "true")
public class QQAutoConfig extends SocialAutoConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private DataSource dataSource;

    /**
     * QQ登录判断此用户是否已经在数据库userconnection表中了，
     * 若没在则直接默认帮我们注册一个用户，并save到表中，
     * 这样一来就无需跳转到注册页面了，直接登录成功即可，因为默认为我们注册了，
     * 看源码发现：
     * JdbcUsersConnectionRepository.findUserIdsWithConnection()方法有一个判断connectionSignUp != null会为我们自动save，
     * 所以我们写一类实现ConnectionSignUp接口。并返回唯一的标识即可。
     */
    @Autowired
    private ConnectionSignUp ctwConnectionSignUp;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        QQProperties qqConfig = securityProperties.getSocial().getQq();
        return new QQConnectionFactory(qqConfig.getProviderId(), qqConfig.getAppId(), qqConfig.getAppSecret());
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());

        /**
         * 表前缀
         */
        if (StringUtils.isNotBlank(securityProperties.getSocial().getTablePrefix())) {
            repository.setTablePrefix(securityProperties.getSocial().getTablePrefix());
        }

        /**
         * 作用在上面说了，QQ登录自动注册用户
         */
        if (StringUtils.equals(securityProperties.getSocial().getAutoSignUp(), "true")) {
            repository.setConnectionSignUp(ctwConnectionSignUp);
        }

        /**
         * Encryptors 加解密工具，我们这里不加密
         */
        return repository;
    }

    /**
     * 下面这个工具类主要解决两个问题：
     * 1、注册过程中如何拿到SpringSocial信息
     * 2、（注册接口能拿到userId（userconnection表的主键userId））注册完成后那怎么将这个userId传递给SpringSocial，
     * 让SpringSocial根据userId拿到用户信息，和注册时填写的基本信息一起保存到db中
     *
     * @return
     */
    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
        return new ProviderSignInUtils(connectionFactoryLocator, this.getUsersConnectionRepository(connectionFactoryLocator)) {
        };
    }

    /**
     * connect/qqConnect：解绑  connect/qqConnected：绑定
     *
     * @return
     */
    @Bean({"connect/qqConnect", "connect/qqConnected"})
    @ConditionalOnMissingBean(name = "qqConnectView")
    public View qqConnectedView() {
        return new CtwConnectView();
    }
}

