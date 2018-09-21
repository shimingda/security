package com.dome.config;

import com.dome.authenticate.AuthenticateProvider;
import com.dome.authenticate.MyCustomUserService;
import com.dome.config.properties.SecurityProperties;
import com.dome.handler.MerryyouAuthenticationfailureHandler;
import com.dome.handler.MerryyouLoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.sql.DataSource;
/**
 * 核心配置文件
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)//prePostEnabled使@PreAuthorize生效
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
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private SessionRegistry sessionRegistry;

    @Autowired
    private InvalidSessionStrategy invalidSessionStrategy;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    //注入数据源
    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Autowired
    public void globalConfigure(AuthenticationManagerBuilder auth){
        auth.authenticationProvider(authenticateProvider());
    }
    // Remove the ROLE_ prefix
    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }
    @Bean
    public AuthenticateProvider authenticateProvider() {
        AuthenticateProvider provider = new AuthenticateProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        return provider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManager manager = super.authenticationManagerBean();
        return manager;
    }
    @Bean
    public JdbcTokenRepositoryImpl tokenRepository(){
        JdbcTokenRepositoryImpl j=new JdbcTokenRepositoryImpl();
        j.setDataSource(dataSource);
        return j;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //权限控制
        http
                .authorizeRequests()
                .antMatchers("/test/user","/session/*","/index","/").permitAll()
                .antMatchers("/test/role").hasAnyRole("admin")
                .antMatchers("/test/permission").hasRole("super_admin")
                .antMatchers("/test/del").access("super_admin")
                .anyRequest().authenticated();

        //登录验证配置post验证
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
                .logoutUrl("/signOut").permitAll()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(logoutSuccessHandler);
        // 禁用csrf防御机制(跨域请求伪造)，这么做在测试和开发会比较方便。
        //session管理
        http
                .sessionManagement()
                .invalidSessionStrategy(invalidSessionStrategy)//session失效策略处理
                .maximumSessions(securityProperties.getSession().getMaximumSessions())//最大session并发数量1
                .maxSessionsPreventsLogin(securityProperties.getSession().isMaxSessionsPreventsLogin())//之后的登录踢掉之前的登录
                .expiredSessionStrategy(sessionInformationExpiredStrategy)//并发过期处理
                .sessionRegistry(sessionRegistry)
        ;
        //http缓存
        http
                .requestCache()
                .requestCache(new HttpSessionRequestCache());
        http
                .csrf().disable();
        // token管理
        http
                .rememberMe()
                .tokenValiditySeconds(securityProperties.getRememberMeSeconds())
                .tokenRepository(tokenRepository())
                .userDetailsService(userService);
        http.httpBasic();
    }
}
