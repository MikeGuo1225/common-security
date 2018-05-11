package com.chentongwei.security.app.social;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/**
 * @author chentongwei@bshf360.com 2018-05-08 15:48
 */
public class OpenIdAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final Object principal;
    private String providerId;

    public OpenIdAuthenticationToken(String openId, String providerId) {
        super(null);
        this.principal = openId;
        this.providerId = providerId;
        setAuthenticated(false);
    }

    public OpenIdAuthenticationToken(Object openId, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = openId;
        super.setAuthenticated(true);
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }

        super.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }

    public String getProviderId() {
        return providerId;
    }
}
