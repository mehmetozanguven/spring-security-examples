package com.mehmetozanguven.springsecurityoauth2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
public class MyOidcUserService extends OidcUserService {
    private static final Logger logger = LoggerFactory.getLogger(MyOidcUserService.class.getSimpleName());
    @Autowired
    private UserService userService;

    // Our OAuth2UserService will apply:
    /**
     * Get the information from the google, such as gmail adress (+)
     *
     * Check whether gmail address is in the database (+)
     * If user isn't defined in the database:
     *  Create a new record for that user
     *  Save the new user to the database
     *  Return appropriate object
     * If user is already defined in the database:
     *  Return appropriate object
     */

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser =  super.loadUser(userRequest);
        try {
            return userService.findUser(userRequest, oidcUser);
        } catch (Exception ex)  {
            logger.error("Error", ex);
            throw new OAuth2AuthenticationException(ex.getMessage());
        }
    }
}
