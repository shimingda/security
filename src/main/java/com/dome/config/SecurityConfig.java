package com.dome.config;

import com.dome.authenticate.AuthenticateProvider;
import com.dome.authenticate.MyCustomUserService;
import com.dome.config.authorize.AuthorizeConfigProvider;
import com.dome.config.properties.SecurityProperties;
import com.dome.config.session.SessionConfigProvider;
import com.dome.config.user.UserConfigProvider;
import com.dome.handler.MerryyouAuthenticationfailureHandler;
import com.dome.handler.MerryyouLoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * 核心配置文件
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthorizeConfigProvider authorizeConfigProvider;

    @Autowired
    private MyCustomUserService userService;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private SessionConfigProvider sessionConfigProvider;

    @Autowired
    private UserConfigProvider userConfigProvider;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");
    }

    @Autowired
    public void globalConfigure(AuthenticationManagerBuilder auth){
        auth.authenticationProvider(authenticateProvider());
    }

    @Bean
    public AuthenticateProvider authenticateProvider() {
        AuthenticateProvider provider = new AuthenticateProvider();
        provider.setUserDetailsService(userService);
       provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManager manager = super.authenticationManagerBean();
        return manager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // token管理
        http
                .rememberMe()
                .tokenValiditySeconds(securityProperties.getRememberMeSeconds())
                .userDetailsService(userService);

        //用户单独配置
        userConfigProvider.config(http);
        //session单独配置
        sessionConfigProvider.config(http);
        //权限角色过滤单独配置
        authorizeConfigProvider.config(http.authorizeRequests());
        // 禁用csrf防御机制(跨域请求伪造)，这么做在测试和开发会比较方便。
        http.csrf().disable();
    }
}
