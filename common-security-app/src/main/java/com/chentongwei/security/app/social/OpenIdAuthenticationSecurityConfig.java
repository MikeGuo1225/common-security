package com.chentongwei.security.app.social;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * @author chentongwei@bshf360.com 2018-03-28 10:30
 */
@Component
public class OpenIdAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private AuthenticationSuccessHandler ctwAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler ctwAuthenticationFailureHandler;

    @Autowired
    private SocialUserDetailsService userDetailsService;

    @Autowired
    private UsersConnectionRepository usersConnectionRepository;

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        OpenIdAuthenticationFilter openIdAuthenticationFilter = new OpenIdAuthenticationFilter();
        openIdAuthenticationFilter.setAuthenticationManager(httpSecurity.getSharedObject(AuthenticationManager.class));
        openIdAuthenticationFilter.setAuthenticationSuccessHandler(ctwAuthenticationSuccessHandler);
        openIdAuthenticationFilter.setAuthenticationFailureHandler(ctwAuthenticationFailureHandler);

        OpenIdAuthenticationProvider openIdAuthenticationProvider= new OpenIdAuthenticationProvider();
        openIdAuthenticationProvider.setUserDetailsService(userDetailsService);
        openIdAuthenticationProvider.setUsersConnectionRepository(usersConnectionRepository);

        httpSecurity.authenticationProvider(openIdAuthenticationProvider)
                .addFilterAfter(openIdAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
