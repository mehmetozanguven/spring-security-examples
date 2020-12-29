package com.mehmetozanguven.springsecuritywithdatabase.config;

import com.mehmetozanguven.springsecuritywithdatabase.services.PostgresqlUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ProjectBeanConfiguration {
    @Bean
    public UserDetailsService userDetailsService(){
       return new PostgresqlUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
