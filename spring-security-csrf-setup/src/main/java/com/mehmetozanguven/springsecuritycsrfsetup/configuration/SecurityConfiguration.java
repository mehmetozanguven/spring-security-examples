package com.mehmetozanguven.springsecuritycsrfsetup.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter  {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
//        http.csrf().disable(); // NOT RECOMMENDED
        http.csrf(c -> {
           c.ignoringAntMatchers("/disabledEndpoint", "/anotherEndpointParent/**");
//           c.csrfTokenRepository(new MyCsrfRepository()); NOT RECOMMENDED
        });
    }
}
