package com.dome.config.authorize;

import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class AuthorizeConfigProviderImpl implements AuthorizeConfigProvider {
    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) throws Exception {

//        config
//                .antMatchers("/","message").permitAll()
//                .antMatchers("/user/*").permitAll();
//                .antMatchers("/oauth/*").permitAll();

//        config.antMatchers("/test/user").hasRole("admin");
        config.anyRequest().authenticated();
//        config.antMatchers("/person/*").access("hasRole('ADMIN') or hasRole('USER')")
//                .antMatchers("/person/{id}").access("@rbacService.checkUserId(authentication,#id)")
//                .anyRequest()
//                .access("@rbacService.hasPermission(request,authentication)");
//        config.antMatchers("/perons").access("hasRole('ADMIN')").anyRequest().authenticated();
        //        config.anyRequest().access("@rbacService.hasPermission(request,authentication)");
        //拦截url设置

    }
}
