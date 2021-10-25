package com.mehmetozanguven.springsecurityoauth2.exception;

import org.springframework.security.core.AuthenticationException;

public class OAuth2CustomException extends AuthenticationException {

    public OAuth2CustomException(String msg) {
        super(msg);
    }
}
