package com.mehmetozanguven.springsecuritymultipleproviders.config;

import com.mehmetozanguven.springsecuritymultipleproviders.service.filters.TokenAuthFilter;
import com.mehmetozanguven.springsecuritymultipleproviders.service.filters.UsernamePasswordAuthFilter;
import com.mehmetozanguven.springsecuritymultipleproviders.service.providers.OtpAuthProvider;
import com.mehmetozanguven.springsecuritymultipleproviders.service.providers.TokenAuthProvider;
import com.mehmetozanguven.springsecuritymultipleproviders.service.providers.UsernamePassswordAuthProvider;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
//@EnableAsync to enable async operations
public class ProjectBeanConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private UsernamePasswordAuthFilter usernamePasswordAuthFilter;

    @Autowired
    private UsernamePassswordAuthProvider usernamePassswordAuthProvider;

    @Autowired
    private OtpAuthProvider otpAuthProvider;

    // second filter and provider
    @Bean
    public TokenAuthFilter tokenAuthFilter() {
        return new TokenAuthFilter();
    }

    @Autowired
    private TokenAuthProvider tokenAuthProvider;


    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(usernamePassswordAuthProvider)
                .authenticationProvider(otpAuthProvider)
                .authenticationProvider(tokenAuthProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAt(usernamePasswordAuthFilter, BasicAuthenticationFilter.class)
            .addFilterAfter(tokenAuthFilter(), BasicAuthenticationFilter.class);
    }

    // If you want to change the SecurityContextHolder, you can use this method:
//    @Bean
//    public InitializingBean initializingBean() {
//        return () -> {
//            SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
//        };
//    }
}
