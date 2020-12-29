package com.mehmetozanguven.springsecuritywithdatabase.services;

import com.mehmetozanguven.springsecuritywithdatabase.entity.UserDTO;
import com.mehmetozanguven.springsecuritywithdatabase.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class PostgresqlUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username){
        Optional<UserDTO> userDTOOptional = userRepository.findUserByUsername(username);
        UserDTO userInDb = userDTOOptional.orElseThrow(() -> new UsernameNotFoundException("Not Found in DB"));
        SecureUser secureUser = new SecureUser(userInDb);
        return secureUser;
    }
}
