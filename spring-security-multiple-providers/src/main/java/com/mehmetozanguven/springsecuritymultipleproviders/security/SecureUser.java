package com.mehmetozanguven.springsecuritymultipleproviders.security;

import com.mehmetozanguven.springsecuritymultipleproviders.entities.UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class SecureUser implements UserDetails {

    private final UserDTO userDTO;

    public SecureUser(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    /**
     * Because I will not look at the authority part for now
     * I am just creating dummy authority for the users
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "read");
    }

    @Override
    public String getPassword() {
        return userDTO.getPassword();
    }

    @Override
    public String getUsername() {
        return userDTO.getUsername();
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
