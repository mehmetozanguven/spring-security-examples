package com.mehmetozanguven.springsecuritymultipleproviders.service.providers;

import com.mehmetozanguven.springsecuritymultipleproviders.service.authentications.UsernamePasswordAuthentication;
import com.mehmetozanguven.springsecuritymultipleproviders.service.model.PostgresqlUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsernamePassswordAuthProvider implements AuthenticationProvider {

    @Autowired
    private PostgresqlUserDetailsService postgresqlUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        // loadByUsername will throw an exception if user is not found
        UserDetails userDetails = postgresqlUserDetailsService.loadUserByUsername(username);

        // after finding the user's details, check with the passwordEncoder
        if (passwordEncoder.matches(password, userDetails.getPassword())){
            // if everything is correct, create a fully authenticated object
            // for this simple project, authorities is hard-coded, do not care about
            return new UsernamePasswordAuthenticationToken(username, password, List.of(() -> "read"));
        }
        throw new BadCredentialsException("BadCredentialException");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthentication.class.equals(authentication);
    }
}
