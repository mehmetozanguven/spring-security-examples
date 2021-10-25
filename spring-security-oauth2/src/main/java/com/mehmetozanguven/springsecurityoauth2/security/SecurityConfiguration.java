package com.mehmetozanguven.springsecurityoauth2.security;

import com.mehmetozanguven.springsecurityoauth2.controller.Urls;
import com.mehmetozanguven.springsecurityoauth2.service.MyLocalUserDetailsService;
import com.mehmetozanguven.springsecurityoauth2.service.MyOidcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final String CLIENT_SECRET = "your_client_secret";
    private static final String CLIENT_ID = "your_client_id";

    @Autowired
    private MyOidcService myOidcService;

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
        // permit specific urls, (js, css, etc...)
        http.authorizeRequests().antMatchers(permittedUrls()).permitAll();

        // anything than the permitted urls must be protected
        http.authorizeRequests().anyRequest().authenticated();

        // configure logout functionality,
        // after logout has occurred, redirects user to the "/"

        http.logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl(Urls.INDEX)
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
        );

        // oauth2 configuration,
        // after successful login, redirects user to the "/home"
        http.oauth2Login()
                .defaultSuccessUrl(Urls.LOGGED_IN_PAGE, true)
                .userInfoEndpoint().oidcUserService(myOidcService);

        // Setup form login authentication
        http.formLogin().loginPage(Urls.INDEX).loginProcessingUrl("/loginProcess").defaultSuccessUrl(Urls.LOGGED_IN_PAGE, true);
    }

    @Bean
    public ClientRegistrationRepository clientRepository() {
        ClientRegistration google = googleClientRegistration();
        return new InMemoryClientRegistrationRepository(google);
    }

    private ClientRegistration googleClientRegistration() {
        return  CommonOAuth2Provider.GOOGLE
                .getBuilder("google")
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .build();
    }

}
