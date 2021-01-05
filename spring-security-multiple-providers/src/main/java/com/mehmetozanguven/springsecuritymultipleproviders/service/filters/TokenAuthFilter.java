package com.mehmetozanguven.springsecuritymultipleproviders.service.filters;

import com.mehmetozanguven.springsecuritymultipleproviders.service.authentications.TokenAuthentication;
import com.mehmetozanguven.springsecuritymultipleproviders.service.holder.AuthorizationTokenHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthFilter extends OncePerRequestFilter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        TokenAuthentication tokenAuthentication = new TokenAuthentication(null, authorization);

        Authentication authResult = authenticationManager.authenticate(tokenAuthentication);
        SecurityContextHolder.getContext().setAuthentication(authResult);
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        if (isPathLogin(request)){
            // if path is login then return true,
            //   which means: do not run the Token authentication,
            //     this authentication must be done by UsernamePasswordAuthFilter
            return true;
        }else{
            // for other endpoints run this authentication
            return false;
        }
    }

    private boolean isPathLogin(HttpServletRequest request){
        return request.getServletPath().equals("/login");
    }
}
