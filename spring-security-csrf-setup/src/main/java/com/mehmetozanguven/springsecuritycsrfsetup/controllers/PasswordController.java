package com.mehmetozanguven.springsecuritycsrfsetup.controllers;

import com.mehmetozanguven.springsecuritycsrfsetup.service.CustomerPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PasswordController {
    private final CustomerPasswordService customerPasswordService;

    public PasswordController(@Autowired CustomerPasswordService customerPasswordService) {
        this.customerPasswordService = customerPasswordService;
    }

    @PostMapping("/changePassword")
    public String changeCustomerPassword(@RequestParam String newPassword, Model model) {
        System.out.println("New Password will be: " + newPassword);
        customerPasswordService.changePassword(newPassword);
        model.addAttribute("newPassword", newPassword);
        return "passwordChanged";
    }
}
