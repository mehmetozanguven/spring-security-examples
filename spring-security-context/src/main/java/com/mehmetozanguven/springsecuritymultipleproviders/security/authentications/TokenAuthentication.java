package com.mehmetozanguven.springsecuritymultipleproviders.security.authentications;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class TokenAuthentication extends UsernamePasswordAuthenticationToken {
    // this constructor creates a Authentication instance which is not fully authenticated
    public TokenAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }

    // this constructor creates a Authentication instance which is fully authenticated
    public TokenAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
