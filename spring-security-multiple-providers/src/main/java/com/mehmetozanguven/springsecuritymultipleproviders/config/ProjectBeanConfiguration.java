package com.mehmetozanguven.springsecuritymultipleproviders.config;

import com.mehmetozanguven.springsecuritymultipleproviders.repositories.OtpRepository;
import com.mehmetozanguven.springsecuritymultipleproviders.security.filters.TokenAuthFilter;
import com.mehmetozanguven.springsecuritymultipleproviders.security.filters.UsernamePasswordAuthFilter;
import com.mehmetozanguven.springsecuritymultipleproviders.security.holder.AuthorizationTokenHolder;
import com.mehmetozanguven.springsecuritymultipleproviders.service.PostgresqlUserDetailsService;
import com.mehmetozanguven.springsecuritymultipleproviders.security.providers.OtpAuthProvider;
import com.mehmetozanguven.springsecuritymultipleproviders.security.providers.TokenAuthProvider;
import com.mehmetozanguven.springsecuritymultipleproviders.security.providers.UsernamePassswordAuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class ProjectBeanConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthorizationTokenHolder authorizationTokenHolder;

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private PostgresqlUserDetailsService postgresqlUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    public UsernamePasswordAuthFilter usernamePasswordAuthFilter() throws Exception {
        return new UsernamePasswordAuthFilter(authenticationManagerBean(), otpRepository, authorizationTokenHolder);
    }

    public UsernamePassswordAuthProvider usernamePassswordAuthProvider() {
        return new UsernamePassswordAuthProvider(postgresqlUserDetailsService, passwordEncoder());
    }

    public OtpAuthProvider otpAuthProvider(){
        return new OtpAuthProvider(otpRepository);
    }


    // second filter and provider
    @Bean
    public TokenAuthFilter tokenAuthFilter() throws Exception {
        return new TokenAuthFilter(authenticationManagerBean());
    }

    public TokenAuthProvider tokenAuthProvider() {
        return new TokenAuthProvider(authorizationTokenHolder);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(usernamePassswordAuthProvider())
                .authenticationProvider(otpAuthProvider())
                .authenticationProvider(tokenAuthProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAt(usernamePasswordAuthFilter(), BasicAuthenticationFilter.class)
            .addFilterAfter(tokenAuthFilter(), BasicAuthenticationFilter.class);
    }
}
