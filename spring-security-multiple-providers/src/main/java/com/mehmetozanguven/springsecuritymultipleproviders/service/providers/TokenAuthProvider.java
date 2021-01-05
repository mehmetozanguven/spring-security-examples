package com.mehmetozanguven.springsecuritymultipleproviders.service.providers;

import com.mehmetozanguven.springsecuritymultipleproviders.service.authentications.TokenAuthentication;
import com.mehmetozanguven.springsecuritymultipleproviders.service.holder.AuthorizationTokenHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TokenAuthProvider implements AuthenticationProvider {
    @Autowired
    private AuthorizationTokenHolder authorizationTokenHolder;

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
