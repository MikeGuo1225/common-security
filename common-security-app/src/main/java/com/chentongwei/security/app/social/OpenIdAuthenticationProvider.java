package com.chentongwei.security.app.social;

import com.chentongwei.security.core.authentication.mobile.SmsCodeAuthenticationToken;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;

import java.util.HashSet;
import java.util.Set;

/**
 * @author chentongwei@bshf360.com 2018-03-28 10:28
 */
public class OpenIdAuthenticationProvider implements AuthenticationProvider {

    private SocialUserDetailsService userDetailsService;

    private UsersConnectionRepository usersConnectionRepository;

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.security.authentication.AuthenticationProvider#
     * authenticate(org.springframework.security.core.Authentication)
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        OpenIdAuthenticationToken authenticationToken = (OpenIdAuthenticationToken) authentication;
        HashSet<String> providerUserIds = new HashSet<>();

        // 获取openId
        providerUserIds.add((String) authenticationToken.getPrincipal());

        // 根据providerId和openId去userconnection表里查询是否存在用户
        Set<String> userIds = usersConnectionRepository.findUserIdsConnectedTo(authenticationToken.getProviderId(), providerUserIds);

        // 判断用户是否唯一
        if (CollectionUtils.isEmpty(userIds) || userIds.size() != 1) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        String userId = userIds.iterator().next();

        UserDetails user = userDetailsService.loadUserByUserId(userId);
        if (null == user) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        OpenIdAuthenticationToken authenticationTokenResult = new OpenIdAuthenticationToken(user, user.getAuthorities());

        authenticationTokenResult.setDetails(authenticationToken.getDetails());

        return authenticationTokenResult;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.security.authentication.AuthenticationProvider#
     * supports(java.lang.Class)
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return OpenIdAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public SocialUserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(SocialUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public UsersConnectionRepository getUsersConnectionRepository() {
        return usersConnectionRepository;
    }

    public void setUsersConnectionRepository(UsersConnectionRepository usersConnectionRepository) {
        this.usersConnectionRepository = usersConnectionRepository;
    }
}