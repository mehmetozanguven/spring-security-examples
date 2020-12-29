package com.mehmetozanguven.springsecuritywithuserdetailsmanager.controller;

import com.mehmetozanguven.springsecuritywithuserdetailsmanager.model.SecureUser;
import com.mehmetozanguven.springsecuritywithuserdetailsmanager.model.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private JdbcUserDetailsManager userDetailsManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @PostMapping("/user")
    public void addUser(@RequestBody UserRequest userRequest){
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userDetailsManager.createUser(new SecureUser(userRequest));
    }
}
