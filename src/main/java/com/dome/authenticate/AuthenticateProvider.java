package com.dome.authenticate;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 认证
 * Created by zhangwen@reconova.com on 2016/12/15.
 */
@Component
public class AuthenticateProvider extends DaoAuthenticationProvider {


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        UserDetails user = this.getUserDetailsService().loadUserByUsername(username);
//        if (true) {
//            throw new BadCredentialsException("用户不存在");
//        }
//
//        if (!user.isEnabled()) {
//            throw new DisabledException("用户被禁用");
//        }
//        Collection<? extends GrantedAuthority> grantedAuthorities = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user, password, null);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        super.additionalAuthenticationChecks(userDetails, authentication);
        if (!userDetails.isEnabled()) {
            throw new DisabledException("账户被禁用，请联系管理员");
        }
    }



}
