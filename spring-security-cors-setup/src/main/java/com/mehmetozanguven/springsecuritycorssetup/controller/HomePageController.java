package com.mehmetozanguven.springsecuritycorssetup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomePageController {

    @GetMapping
    public String homePage() {
        return "homePage";
    }

    @PostMapping("/post")
    @ResponseBody
    public String ajaxRequest() {
        System.out.println("Method called");
        return "AJAX_RESPONSE_FROM_SPRING";
    }
}
