package com.chentongwei.security.app.config;

import com.chentongwei.security.core.properties.SecurityProperties;
import com.chentongwei.security.core.properties.oauth2.OAuth2ClientProperties;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * 认证服务配置
 * 什么都不用我们做，只需要一个EnableAuthorizationServer注解即可。
 * Demo项目引用了我们这个app项目，所以Demo项目的Application启动后就是一个认证服务器了。
 * 所以它就能够提供OAuth协议的四种授权模式了。
 *
 * @author chentongwei@bshf360.com 2018-04-08 11:13
 */
@Configuration
@EnableAuthorizationServer
public class CtwAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private TokenStore redisTokenStore;
    @Autowired(required = false)
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    @Autowired(required = false)
    private TokenEnhancer jwtTokenEnhancer;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(redisTokenStore)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
        if (null != jwtAccessTokenConverter && null != jwtTokenEnhancer) {
            TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
            List<TokenEnhancer> enhancers = new ArrayList<>();

            enhancers.add(jwtTokenEnhancer);
            enhancers.add(jwtAccessTokenConverter);
            tokenEnhancerChain.setTokenEnhancers(enhancers);

            endpoints
                    .tokenEnhancer(tokenEnhancerChain)
                    .accessTokenConverter(jwtAccessTokenConverter);
        }
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        if (null != securityProperties.getOauth2().getClient()) {
            builder.withClient(securityProperties.getOauth2().getClient().getClientId())
                    .secret(securityProperties.getOauth2().getClient().getClientSecret())
                    .accessTokenValiditySeconds(securityProperties.getOauth2().getClient().getAccessTokenValidSeconds())
                    // 一个月的refresh_token时长
                    .refreshTokenValiditySeconds(2592000)
                    .authorizedGrantTypes("refresh_token", "authorization_code", "password")
                    .scopes("all");
        }
    }
}
