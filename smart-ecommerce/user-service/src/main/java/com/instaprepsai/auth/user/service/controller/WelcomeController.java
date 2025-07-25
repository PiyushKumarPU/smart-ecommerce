package com.instaprepsai.auth.user.service.controller;

import com.instaprepsai.auth.user.service.annotations.UsersRestController;
import org.springframework.web.bind.annotation.GetMapping;

@UsersRestController
public class WelcomeController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello from User Service";
    }
}
