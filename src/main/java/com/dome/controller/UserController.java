package com.dome.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
   @RequestMapping("/test")
    public String getUsers() {
        return "test";
    }
    @RequestMapping("/login")
    public String login() {
        return "Hello Spring Security";
    }
}
