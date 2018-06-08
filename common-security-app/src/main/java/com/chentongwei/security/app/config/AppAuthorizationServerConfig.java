package com.chentongwei.security.app.config;

import com.chentongwei.security.app.properties.SecurityProperties;
import com.chentongwei.security.app.properties.oauth2.ClientProperties;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.List;

/**
 * 认证服务配置
 *
 * @author chentongwei@bshf360.com 2018-06-07 19:33
 */
//@Configuration
//@EnableAuthorizationServer
public class AppAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenStore redisTokenStore;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(redisTokenStore)
                .accessTokenConverter(jwtAccessTokenConverter())
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
//        jwtAccessTokenConverter.setSigningKey(securityProperties.getOauth2().getJwtSigningKey());
        return jwtAccessTokenConverter;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        /*if (CollectionUtils.isNotEmpty(securityProperties.getOauth2().getClients())) {
            List<ClientProperties> clientList = securityProperties.getOauth2().getClients();
            for (int i = 0, size = clientList.size(); i < size; i ++) {
                builder.withClient(clientList.get(i).getClientId())
                        .secret(clientList.get(i).getClientSecret())
                        .accessTokenValiditySeconds(clientList.get(i).getAccessTokenValidSeconds())
                        // 一个月的refresh_token时长
                        .refreshTokenValiditySeconds(2592000)
                        .authorizedGrantTypes("refresh_token", "authorization_code", "password")
                        .scopes("all");
            }
        }*/
    }
}
