package com.mehmetozanguven.springsecuritymultipleproviders.service;

import com.mehmetozanguven.springsecuritymultipleproviders.entities.UserDTO;
import com.mehmetozanguven.springsecuritymultipleproviders.repositories.UserRepository;
import com.mehmetozanguven.springsecuritymultipleproviders.security.SecureUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class PostgresqlUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public PostgresqlUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO userInDb = userRepository.findUserDTOByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException("User not found in the db"));
        return new SecureUser(userInDb);
    }
}
