package com.mehmetozanguven.springsecurityoauth2.security;

import com.mehmetozanguven.springsecurityoauth2.controller.Urls;
import com.mehmetozanguven.springsecurityoauth2.service.MyLocalUserDetailsService;
import com.mehmetozanguven.springsecurityoauth2.service.MyOidcUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyOidcUserService myOidcUserService;

    @Bean
    public UserDetailsService userDetailsService() {
        return new MyLocalUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    private String[] permittedUrls() {
        return new String[] {
                Urls.INDEX,
                Urls.REGISTER_PAGE,
                "/js/**",
                "/css/**",
                "/assets/**"
        };
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // permit specific urls (js, css, etc..)
        http.authorizeRequests().antMatchers(permittedUrls()).permitAll();

        // anything than the permitted urls must be protected
        http.authorizeRequests().anyRequest().authenticated();

        http.logout(logout -> {
            logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
            logout.logoutSuccessUrl(Urls.INDEX);
            logout.deleteCookies("JSESSIONID");
            logout.invalidateHttpSession(true);
            logout.clearAuthentication(true);
        });

        // oauth2 configuration
        // after successful login, redirect user to the "/home"
        http.oauth2Login()
                .defaultSuccessUrl(Urls.LOGGED_IN_PAGE, true)
                .userInfoEndpoint().oidcUserService(myOidcUserService);

        http.formLogin()
                .loginPage(Urls.INDEX)
                .loginProcessingUrl("/loginProcess")
                .defaultSuccessUrl(Urls.LOGGED_IN_PAGE, true);
    }
}
