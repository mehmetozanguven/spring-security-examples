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
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Optional<UserDTO> findByUsername(String email) {
       return userRepository.findByEmail(email);
    }

    public UserDTO createNewUser(String email, Provider provider) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(email);
        userDTO.setProvider(provider);
        userDTO.setRole("READ");
        return userDTO;
    }

    @Transactional
    public void saveNewUser(UserDTO userDTO) {
        userRepository.save(userDTO);
    }

    @Transactional
    public SecureUser findUser(OidcUserRequest userRequest, OidcUser oidcUser) {
        GoogleOAuth2Request googleOAuth2Request = new GoogleOAuth2Request(userRequest.getClientRegistration().getRegistrationId(), oidcUser.getAttributes());

        Optional<UserDTO> userInDb = findByUsername(googleOAuth2Request.getEmail());
        if (userInDb.isEmpty()) {
            logger.info("new user with email: {}", googleOAuth2Request.getEmail());
            UserDTO userDTO = createNewUser(googleOAuth2Request.getEmail(), Provider.GOOGLE);
            saveNewUser(userDTO);
            return SecureUser.createFromOidcUser(oidcUser, userDTO);
        } else {
            return SecureUser.createFromOidcUser(oidcUser, userInDb.get());
        }
    }
}
