package com.mehmetozanguven.springsecuritycustomfilter.security.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class MyCustomAuthentication extends UsernamePasswordAuthenticationToken {

    public MyCustomAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public MyCustomAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
