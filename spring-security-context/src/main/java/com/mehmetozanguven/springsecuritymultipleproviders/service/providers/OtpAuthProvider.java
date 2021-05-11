package com.mehmetozanguven.springsecuritymultipleproviders.service.providers;

import com.mehmetozanguven.springsecuritymultipleproviders.entities.OtpDTO;
import com.mehmetozanguven.springsecuritymultipleproviders.repositories.OtpRepository;
import com.mehmetozanguven.springsecuritymultipleproviders.service.authentications.OtpAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OtpAuthProvider implements AuthenticationProvider {

    @Autowired
    private OtpRepository otpRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String otp = (String) authentication.getCredentials();

        OtpDTO otpDTO = otpRepository.findOtpDtoByUsername(username)
                .orElseThrow(() -> new BadCredentialsException("Bad Otp Credentials"));

        return new OtpAuthentication(username, otp, List.of(() -> "read"));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OtpAuthentication.class.equals(authentication);
    }
}
