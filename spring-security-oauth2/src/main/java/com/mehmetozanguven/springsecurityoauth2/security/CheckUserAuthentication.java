package com.mehmetozanguven.springsecurityoauth2.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CheckUserAuthentication {
    public static boolean isUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       return (!(authentication instanceof AnonymousAuthenticationToken));
    }
}
