package com.mehmetozanguven.springsecuritycsrfsetup.service;

import org.springframework.stereotype.Service;

@Service
public class CustomerPasswordService {

    public void changePassword(String newPassword) {
        // dummy method
        System.out.println("Customer password was changed to: " + newPassword);
    }
}
