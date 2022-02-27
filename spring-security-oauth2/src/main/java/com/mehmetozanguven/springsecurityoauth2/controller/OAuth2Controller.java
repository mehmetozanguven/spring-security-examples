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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OAuth2Controller {
    private static final Logger logger = LoggerFactory.getLogger(OAuth2Controller.class.getSimpleName());

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping(value = Urls.INDEX)
    public String getIndexPage() {
        if (CheckUserAuthentication.isUserAuthenticated()) {
            return "logged_in_users";
        }
        return "not_logged_in_page";
    }

    @GetMapping(value = Urls.LOGGED_IN_PAGE)
    public String loggedInUserPage() {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        SecureUser loggedInUser = (SecureUser) currentUser.getPrincipal();

        logger.info("Authentication: {}", SecurityContextHolder.getContext().getAuthentication());
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
        if (userService.findByEmail(registerUser.getUsername()).isPresent()) {
            throw new RuntimeException("User with the given username already registered");
        }

        UserDTO newUser = new UserDTO();
        newUser.setRole("READ");
        newUser.setProvider(Provider.LOCAL);
        newUser.setEmail(registerUser.getUsername());
        newUser.setPassword(passwordEncoder.encode(registerUser.getPassword()));

        userService.saveNewUser(newUser);
        return "not_logged_in_page";
    }
}

