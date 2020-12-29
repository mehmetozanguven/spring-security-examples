package com.mehmetozanguven.springsecuritywithuserdetailsmanager.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class SecureUser implements UserDetails {

    private final UserRequest userRequest;

    public SecureUser(UserRequest userRequest) {
        this.userRequest = userRequest;
    }

    /**
     * Do not care about authority right now,
     * I am just filling with dummy authorities
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "read");
    }

    /**
     * Because we do not have any logic for this method
     * just return true to pass.
     */
    @Override
    public String getPassword() {
        return this.userRequest.getPassword();
    }

    /**
     * Because we do not have any logic for this method
     * just return true to pass.
     */
    @Override
    public String getUsername() {
        return this.userRequest.getUsername();
    }

    /**
     * Because we do not have any logic for this method
     * just return true to pass.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Because we do not have any logic for this method
     * just return true to pass.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Because we do not have any logic for this method
     * just return true to pass.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Because we do not have any logic for this method
     * just return true to pass.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
