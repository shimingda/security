package com.dome.config.bean;

import com.dome.config.properties.SecurityProperties;
import com.dome.handler.MyLogoutSuccessHandler;
import com.dome.session.MerryyouExpiredSessionStrategy;
import com.dome.session.MerryyouInvalidSessionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

@Configuration
public class SessionSecurityBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    public InvalidSessionStrategy invalidSessionStrategy(){
        return new MerryyouInvalidSessionStrategy(securityProperties.getSession().getSessionInvalidUrl());
    }

    @Bean
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy(){
        return new MerryyouExpiredSessionStrategy(securityProperties.getSession().getSessionExpiredUrl());
    }

    @Bean
    @ConditionalOnMissingBean(org.springframework.security.web.authentication.logout.LogoutSuccessHandler.class)
    public org.springframework.security.web.authentication.logout.LogoutSuccessHandler logoutSuccessHandler(){
        return new MyLogoutSuccessHandler(securityProperties.getSignOutUrl());
    }
}
