package com.dome.config.token;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public interface TokenConfigProvider {

    void config(HttpSecurity http);
}
