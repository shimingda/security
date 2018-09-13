package com.dome.config.bean;

import com.dome.authenticate.MyCustomUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * bean配置
 *
 */
@Configuration
public class BeanConfig {

    @Bean
    public UserDetailsService userDetailsService(){
        return new MyCustomUserService();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

