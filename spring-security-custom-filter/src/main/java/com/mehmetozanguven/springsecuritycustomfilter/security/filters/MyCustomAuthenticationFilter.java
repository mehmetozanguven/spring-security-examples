package com.mehmetozanguven.springsecuritycustomfilter.security.filters;

import com.mehmetozanguven.springsecuritycustomfilter.security.authentication.MyCustomAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyCustomAuthenticationFilter implements Filter {

    @Autowired
    private AuthenticationManager authenticationManager;

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
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
