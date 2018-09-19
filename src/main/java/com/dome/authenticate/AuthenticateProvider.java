package com.dome.authenticate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 认证
 */
@Component
public class AuthenticateProvider extends DaoAuthenticationProvider {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        UserDetails user = this.getUserDetailsService().loadUserByUsername(username);

        logger.debug("validate:" + username + "###" + password);
        logger.debug("validate:" + username + "###" + user.getPassword());
        boolean passwordValid =bCryptPasswordEncoder.matches(password,user.getPassword());
        if (!passwordValid) {
            throw new BadCredentialsException("密码错误");
        }
        if (user.isEnabled()) {
            throw new BadCredentialsException("用户被禁用");
        }
//        Collection<? extends GrantedAuthority> grantedAuthorities = user.getAuthorities();
//        return new UsernamePasswordAuthenticationToken(user, password,grantedAuthorities);
        Collection<? extends GrantedAuthority> grantedAuthorities=AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_admin");
        return new UsernamePasswordAuthenticationToken(user, password,grantedAuthorities);

    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        super.additionalAuthenticationChecks(userDetails, authentication);
        if (!userDetails.isEnabled()) {
            throw new DisabledException("账户被禁用，请联系管理员");
        }
    }
}
