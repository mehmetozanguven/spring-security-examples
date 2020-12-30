package com.mehmetozanguven.springsecuritycustomfilter.security.providers;

import com.mehmetozanguven.springsecuritycustomfilter.security.authentication.MyCustomAuthentication;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class MyCustomAuthenticationProvider implements AuthenticationProvider {
    private final String secretKey = "password";

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String requestKey = authentication.getName();
        if (requestKey.equals(secretKey)){
            MyCustomAuthentication fullyAuthenticated = new MyCustomAuthentication(null, null, null);
            return fullyAuthenticated;
        }else{
            throw new BadCredentialsException("Header value is not correct");
        }
    }

    /**
     * If authentication type is MyCustomAuthentication, then
     *  apply this provider
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return MyCustomAuthentication.class.equals(authentication);
    }
}
