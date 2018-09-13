package com.dome.config.session;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;

public interface SessionConfigProvider {
    void  config(HttpSecurity configurer) throws Exception;
}
