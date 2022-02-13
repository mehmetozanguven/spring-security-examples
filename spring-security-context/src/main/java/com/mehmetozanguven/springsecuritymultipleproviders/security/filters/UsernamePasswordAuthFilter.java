package com.mehmetozanguven.springsecuritymultipleproviders.security.filters;

import com.mehmetozanguven.springsecuritymultipleproviders.entities.OtpDTO;
import com.mehmetozanguven.springsecuritymultipleproviders.repositories.OtpRepository;
import com.mehmetozanguven.springsecuritymultipleproviders.security.authentications.OtpAuthentication;
import com.mehmetozanguven.springsecuritymultipleproviders.security.authentications.UsernamePasswordAuthentication;
import com.mehmetozanguven.springsecuritymultipleproviders.security.holder.AuthorizationTokenHolder;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class UsernamePasswordAuthFilter extends OncePerRequestFilter {
    private AuthenticationManager authenticationManager;
    private OtpRepository otpRepository;
    private AuthorizationTokenHolder authorizationTokenHolder;

    public UsernamePasswordAuthFilter(AuthenticationManager authenticationManager,
                                      OtpRepository otpRepository,
                                      AuthorizationTokenHolder authorizationTokenHolder) {
        this.authenticationManager = authenticationManager;
        this.otpRepository = otpRepository;
        this.authorizationTokenHolder = authorizationTokenHolder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String username = httpServletRequest.getHeader("username");
        String password = httpServletRequest.getHeader("password");
        String otp = httpServletRequest.getHeader("otp");

        if (otp == null){
            // * if there is no one-time-password, then generate an OTP
            // 1) first make sure that, correct user is connecting you system
            UsernamePasswordAuthentication usernamePasswordAuthentication =
                    new UsernamePasswordAuthentication(username, password);
            // 2) authManager will find the correct provider for that authentication
            Authentication resultForUsernamePassword = authenticationManager.authenticate(usernamePasswordAuthentication);
            // 3) Because authenticationManager.authenticate will return fully authenticated instance
            // ( otherwise it must throw and error), You can generate otp for authenticatied user

            // 4) Generate new otp
            String otpCode = RandomStringUtils.randomAlphabetic(10);

            // 5) save this new one-time-password for that user.
            OtpDTO otpDTO = new OtpDTO();
            otpDTO.setUsername(username);
            otpDTO.setOtp(otpCode);
            otpRepository.save(otpDTO);

        }else{
            // if there is a one-time-password, authenticate user via one-time-password(otp)
            OtpAuthentication otpAuthentication = new OtpAuthentication(username, otp);
            // authManager will find the correct provider for that authentication
            Authentication resultForOtp = authenticationManager.authenticate(otpAuthentication);
            String authValue = UUID.randomUUID().toString();
            authorizationTokenHolder.add(authValue);
            httpServletResponse.setHeader("Authorization", authValue);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        if (isPathLogin(request)){
            // if path is login then return false, which means: call the doFilterInternal
            return false;
        }else{
            // do not call doFilterInternal
            return true;
        }
    }

    private boolean isPathLogin(HttpServletRequest request){
        return request.getServletPath().equals("/login");
    }
}
