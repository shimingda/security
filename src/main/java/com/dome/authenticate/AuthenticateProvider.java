package com.dome.authenticate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 认证
 */
@Component
public class AuthenticateProvider extends DaoAuthenticationProvider {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        UserDetails user = this.getUserDetailsService().loadUserByUsername(username);

        if (user.getPassword().equals(passwordEncoder.encode(password))){
            throw new BadCredentialsException("用户名密码不匹配");
        }
        if (user.isEnabled()) {
            throw new BadCredentialsException("用户被禁用");
        }
        Collection<? extends GrantedAuthority> grantedAuthorities = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user, password, grantedAuthorities);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        super.additionalAuthenticationChecks(userDetails, authentication);
        if (!userDetails.isEnabled()) {
            throw new DisabledException("账户被禁用，请联系管理员");
        }
    }



}
