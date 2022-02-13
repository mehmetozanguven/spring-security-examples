package com.mehmetozanguven.springsecuritymultipleproviders.security.holder;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class AuthorizationTokenHolder {
    private Set<String> tokens;

    public AuthorizationTokenHolder() {
        this.tokens = new HashSet<>();
    }

    public void add(String token) {
        tokens.add(token);
    }

    public boolean contains(String token) {
        return tokens.contains(token);
    }
}
