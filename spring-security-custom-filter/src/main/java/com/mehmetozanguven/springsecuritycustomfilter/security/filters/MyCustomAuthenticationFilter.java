package com.mehmetozanguven.springsecuritycustomfilter.security.filters;

import com.mehmetozanguven.springsecuritycustomfilter.security.authentication.MyCustomAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyCustomAuthenticationFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(MyCustomAuthenticationFilter.class.getSimpleName());

    private AuthenticationManager authenticationManager;

    public MyCustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String authorization = httpServletRequest.getHeader("CustomAuth");

        MyCustomAuthentication customAuthentication = new MyCustomAuthentication(authorization, null);

        try{
            Authentication authResult = authenticationManager.authenticate(customAuthentication);

            // In real case, there is no need to check isAuthenticated method
            // because when authentication fails, it must throw an error
            if (authResult.isAuthenticated()){
                // If I have fully authentication instance, add to the security context
                // do not think about the security context for now..
                SecurityContextHolder.getContext().setAuthentication(authResult);
                filterChain.doFilter(servletRequest, servletResponse);
            }else{
                httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        }catch (AuthenticationException authenticationException){
            logger.warn("Authentication failed: ", authenticationException);
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
