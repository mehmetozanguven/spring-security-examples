package com.mehmetozanguven.springsecurityoauth2.controller;

import com.mehmetozanguven.springsecurityoauth2.dto.Provider;
import com.mehmetozanguven.springsecurityoauth2.dto.UserDTO;
import com.mehmetozanguven.springsecurityoauth2.model.RegisterUser;
import com.mehmetozanguven.springsecurityoauth2.model.SecureUser;
import com.mehmetozanguven.springsecurityoauth2.security.CheckUserAuthentication;
import com.mehmetozanguven.springsecurityoauth2.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OAuth2Controller {
    private static final Logger logger = LoggerFactory.getLogger(OAuth2Controller.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @GetMapping(value = Urls.INDEX)
    public String getIndexPage() {
        if (CheckUserAuthentication.isUserAuthenticated()) {
            return "logged_in_users.html";
        }
        return "not_logged_in_page";
    }

    @GetMapping(value = Urls.LOGGED_IN_PAGE)
    public String loggedInUserPage(Model model) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication currentUser = securityContext.getAuthentication();
        SecureUser loggedInUser = (SecureUser) currentUser.getPrincipal();
        logger.info("Logged-in user: {}", loggedInUser);
        return "logged_in_users";
    }

    @GetMapping(value = Urls.REGISTER_PAGE)
    public String openLoginPage(Model model) {
        if (CheckUserAuthentication.isUserAuthenticated()) {
            return "logged_in_users";
        }
        model.addAttribute("register", new RegisterUser());
        return "register_page";
    }

    @PostMapping(value = Urls.REGISTER_PAGE)
    public String doRegister(@ModelAttribute RegisterUser registerUser) {
        UserDTO newUser = new UserDTO();
        newUser.setRole("READ");
        newUser.setEmail(registerUser.getUsername());
        newUser.setProvider(Provider.LOCAL);
        newUser.setPassword(passwordEncoder.encode(registerUser.getPassword()));
        if (userService.findByUsername(registerUser.getUsername()).isPresent()) {
            throw new RuntimeException("Already exists");
        }
        userService.saveNewUser(newUser);
        return "not_logged_in_page";
    }
}
