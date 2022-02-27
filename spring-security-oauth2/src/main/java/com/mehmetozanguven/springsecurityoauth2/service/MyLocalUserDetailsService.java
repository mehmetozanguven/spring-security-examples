package com.mehmetozanguven.springsecurityoauth2.service;


import com.mehmetozanguven.springsecurityoauth2.dto.UserDTO;
import com.mehmetozanguven.springsecurityoauth2.model.SecureUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class MyLocalUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserDTO> userInDb = userService.findByEmail(username);
        if (userInDb.isEmpty()) {
            throw new UsernameNotFoundException("There is no username with the given username: " + username);
        }
        return SecureUser.createFromBasicAuthentication(userInDb.get());
    }
}
