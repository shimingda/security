package com.dome.config.session;

import com.dome.config.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class SessionConfigProviderImpl implements SessionConfigProvider {
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;
    @Override
    public void config(HttpSecurity configurer) throws Exception {

        //session管理
        configurer
                .sessionManagement()
                //  .invalidSessionStrategy(invalidSessionStrategy)
                .invalidSessionUrl("/login")//session失效跳转页面
                .maximumSessions(securityProperties.getSession().getMaximumSessions())//最大session并发数量1
                .maxSessionsPreventsLogin(securityProperties.getSession().isMaxSessionsPreventsLogin())//之后的登录踢掉之前的登录
                .expiredSessionStrategy(sessionInformationExpiredStrategy);
    }
}
