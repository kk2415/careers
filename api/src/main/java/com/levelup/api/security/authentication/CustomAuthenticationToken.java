package com.levelup.api.security.authentication;

import com.levelup.api.security.userdetails.LoginType;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;
    private Object credentials;
    private LoginType loginType;

    public CustomAuthenticationToken(Object principal, Object credentials, LoginType loginType, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        this.loginType = loginType;
    }

    public static CustomAuthenticationToken authenticated(Object principal, Object credentials, LoginType loginType, Collection<? extends GrantedAuthority> authorities) {
        return new CustomAuthenticationToken(principal, credentials, loginType, authorities);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    public LoginType getLoginType() {
        return loginType;
    }
}
