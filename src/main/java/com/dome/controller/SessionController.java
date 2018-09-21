package com.dome.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {

    @RequestMapping("/session/invalid")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)

    public String invalid() {
        return "session invalid";
    }
    @RequestMapping("/session/expired")

    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public String expired() {

        return "session expired";
    }
    @RequestMapping("/signOutUrl")
    public String signOutUrl() {
        return "signOutUrl";
    }
}
