package com.mehmetozanguven.springsecuritymultipleproviders.security.providers;

import com.mehmetozanguven.springsecuritymultipleproviders.entities.OtpDTO;
import com.mehmetozanguven.springsecuritymultipleproviders.repositories.OtpRepository;
import com.mehmetozanguven.springsecuritymultipleproviders.security.authentications.OtpAuthentication;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.List;

public class OtpAuthProvider implements AuthenticationProvider {
    private OtpRepository otpRepository;

    public OtpAuthProvider(OtpRepository otpRepository) {
        this.otpRepository = otpRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String otp = (String) authentication.getCredentials();

        OtpDTO otpInDb = otpRepository.findOtpDtoByUsername(username, otp)
                .orElseThrow(() ->  new BadCredentialsException("There is no otp for the given username: " + username));


        return new OtpAuthentication(username, otp, List.of(() -> "read"));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OtpAuthentication.class.equals(authentication);
    }
}
