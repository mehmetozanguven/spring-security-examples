package com.mehmetozanguven.springsecuritycustomfilter.config;

import com.mehmetozanguven.springsecuritycustomfilter.security.filters.MyCustomAuthenticationFilter;
import com.mehmetozanguven.springsecuritycustomfilter.security.providers.MyCustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class ProjectBeanConfiguration extends WebSecurityConfigurerAdapter {

    public MyCustomAuthenticationFilter myCustomAuthenticationFilter() throws Exception {
        return new MyCustomAuthenticationFilter(authenticationManagerBean());
    }

    public MyCustomAuthenticationProvider myCustomAuthenticationProvider() {
        return new MyCustomAuthenticationProvider();
    }



    /**
     * Configure the custom provider
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(myCustomAuthenticationProvider());
    }

    /**
     * Configure the filter, basically replace the
     *  BasicAuthenticationFilter with the custom one
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAt(myCustomAuthenticationFilter(), BasicAuthenticationFilter.class);
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
