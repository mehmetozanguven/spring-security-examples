package com.mehmetozanguven.springsecurityauthenticationprovider.config;

import com.mehmetozanguven.springsecurityauthenticationprovider.security.MyCustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class ProjectBeanConfiguration  extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyCustomAuthenticationProvider myCustomAuthenticationProvider;

    @Bean
    public UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        UserDetails dummyUser = User.withUsername("dummy_user")
                                    .password("1234")
                                    .authorities("read")
                                    .build();
        inMemoryUserDetailsManager.createUser(dummyUser);
        return inMemoryUserDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(myCustomAuthenticationProvider);
    }
}
