package com.chentongwei.security.app.config;

import com.chentongwei.security.app.jwt.CtwTokenEnhancer;
import com.chentongwei.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * 负责令牌的存取
 *
 * @author chentongwei@bshf360.com 2018-05-09 10:49
 */
@Configuration
@ConditionalOnProperty(prefix = "com.chentongwei.security.oauth2", name = "storeType", havingValue = "redis")
public class TokenStoreConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public TokenStore redisTokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

    @Configuration
    /**
     * 若在配置文件里写com.chentongwei.security.oauth2.storeType=jwt，则加载此静态类
     * 或者
     * 根本没写com.chentongwei.security.oauth2.storeType这个属性，则也加载此静态类（matchIfMissing = true的作用）
     */
    @ConditionalOnProperty(prefix = "com.chentongwei.security.oauth2", name = "storeType", havingValue = "jwt", matchIfMissing = true)
    public static class JwtTokenConfig {

        @Autowired
        private SecurityProperties securityProperties;

        @Bean
        public TokenStore jwtTokenStore() {
            return new JwtTokenStore(jwtAccessTokenConverter());
        }

        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter() {
            JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
            jwtAccessTokenConverter.setSigningKey(securityProperties.getOauth2().getJwtSigningKey());
            return jwtAccessTokenConverter;
        }

        /**
         * 自定义jwt返回值。
         * @return
         */
        @Bean
        /**
         * 重写一个类，bean的id为jwtTokenEnhancer即可自定义返回值的业务逻辑
         */
        @ConditionalOnMissingBean(name = "jwtTokenEnhancer")
        public TokenEnhancer jwtTokenEnhancer() {
            return new CtwTokenEnhancer();
        }

    }

}
