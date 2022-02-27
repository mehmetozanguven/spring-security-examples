package com.mehmetozanguven.springsecurityoauth2.service;


import com.mehmetozanguven.springsecurityoauth2.dto.Provider;
import com.mehmetozanguven.springsecurityoauth2.dto.UserDTO;
import com.mehmetozanguven.springsecurityoauth2.model.GoogleOAuth2Request;
import com.mehmetozanguven.springsecurityoauth2.model.SecureUser;
import com.mehmetozanguven.springsecurityoauth2.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class.getSimpleName());

    @Autowired
    private UserRepository userRepository;


    public Optional<UserDTO> findByEmail(String username) {
        return userRepository.findByEmail(username);
    }


    public void saveNewUser(UserDTO userDTO) {
        userRepository.save(userDTO);
    }

    public SecureUser findUser(OidcUserRequest userRequest, OidcUser oidcUser) {
        GoogleOAuth2Request googleOAuth2Request = new GoogleOAuth2Request(userRequest.getClientRegistration().getRegistrationId(), oidcUser.getAttributes());

        Optional<UserDTO> userInDb = findByEmail(googleOAuth2Request.getEmail());
        if (userInDb.isEmpty()) {
            logger.info("New user from the google with email: {}", googleOAuth2Request.getEmail());
            UserDTO newUser = createNewUser(googleOAuth2Request.getEmail(), Provider.GOOGLE);
            saveNewUser(newUser);
            return SecureUser.createSecureUserFromOidcUser(oidcUser, newUser);
        } else {
            return SecureUser.createSecureUserFromOidcUser(oidcUser, userInDb.get());
        }
    }

    private UserDTO createNewUser(String username, Provider provider) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(username);
        userDTO.setProvider(provider);
        userDTO.setRole("READ");
        return userDTO;
    }
}
