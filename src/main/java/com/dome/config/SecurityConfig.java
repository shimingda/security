package com.dome.config;

import com.dome.authenticate.AuthenticateProvider;
import com.dome.authenticate.MyCustomUserService;
import com.dome.config.properties.SecurityProperties;
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
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * 核心配置文件
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyCustomUserService userService;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    @Autowired
    private MerryyouLoginSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private MerryyouAuthenticationfailureHandler myAuthenticationFailHandler;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user").password("password").roles("admin");
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
        //权限控制
        http
                .authorizeRequests()
                .antMatchers("/user/save").permitAll()
                .antMatchers("/test/role").hasAnyRole("admin")
                .antMatchers("/test/permission").hasRole("super_admin")
                .antMatchers("/user/permission").access("super_admin")
                .anyRequest().authenticated();
        //session管理
        http
                .sessionManagement()
                //  .invalidSessionStrategy(invalidSessionStrategy)
                .invalidSessionUrl("/login")//session失效跳转页面
                .maximumSessions(securityProperties.getSession().getMaximumSessions())//最大session并发数量1
                .maxSessionsPreventsLogin(securityProperties.getSession().isMaxSessionsPreventsLogin())//之后的登录踢掉之前的登录
                .expiredSessionStrategy(sessionInformationExpiredStrategy);
        //登录验证配置
        http
                .formLogin()
                .loginProcessingUrl("/user/login2")
                .usernameParameter("username")
                .passwordParameter("password")
                //　自定义的登录验证成功或失败后的去向
                .successHandler(myAuthenticationSuccessHandler)
                // .successHandler(appLoginInSuccessHandler)
                .failureHandler(myAuthenticationFailHandler);
        // 安全退出用户
        http
                .logout()
                .logoutUrl("signOut").permitAll()
                .deleteCookies("")
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/");
        // 禁用csrf防御机制(跨域请求伪造)，这么做在测试和开发会比较方便。
        http
                .csrf().disable();
        // token管理
        http
                .rememberMe()
                .tokenValiditySeconds(securityProperties.getRememberMeSeconds())
                .userDetailsService(userService);
    }
}
