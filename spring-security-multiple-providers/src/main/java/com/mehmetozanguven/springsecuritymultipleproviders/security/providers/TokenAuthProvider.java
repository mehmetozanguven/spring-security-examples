package com.mehmetozanguven.springsecuritymultipleproviders.security.providers;

import com.mehmetozanguven.springsecuritymultipleproviders.security.authentications.TokenAuthentication;
import com.mehmetozanguven.springsecuritymultipleproviders.security.holder.AuthorizationTokenHolder;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.List;

public class TokenAuthProvider implements AuthenticationProvider {
    private AuthorizationTokenHolder authorizationTokenHolder;

    public TokenAuthProvider(AuthorizationTokenHolder authorizationTokenHolder) {
        this.authorizationTokenHolder = authorizationTokenHolder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String authorizationToken = (String) authentication.getCredentials();
        boolean isCorrectToken = authorizationTokenHolder.contains(authorizationToken);

        if (isCorrectToken){
            return new TokenAuthentication(null, authorizationToken, List.of(() -> "read"));
        }else {
            throw new BadCredentialsException("Authorization value is not correct");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return TokenAuthentication.class.equals(authentication);
    }
}
