package com.dome.config.user;

import com.dome.config.authorize.AuthorizeConfigProvider;
import com.dome.handler.MerryyouAuthenticationfailureHandler;
import com.dome.handler.MerryyouLoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class UserConfigProviderImpl implements UserConfigProvider {

    @Autowired
    private MerryyouLoginSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private MerryyouAuthenticationfailureHandler myAuthenticationFailHandler;
    @Override
    public void config(HttpSecurity configurer)  throws Exception{
        //登录验证配置
        configurer
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/user/login2")
                .usernameParameter("username")
                .passwordParameter("password")
                //　自定义的登录验证成功或失败后的去向
                .successHandler(myAuthenticationSuccessHandler)
                // .successHandler(appLoginInSuccessHandler)
                .failureHandler(myAuthenticationFailHandler);
        // 安全退出用户
        configurer
                .logout()
                .logoutUrl("signOut").permitAll()
                .deleteCookies("")
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/");
    }
}
