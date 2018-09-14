package com.dome.authenticate;


import com.dome.entity.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class MyCustomUserService implements UserDetailsService {

//    @Autowired
//    private SysUserMapper sysUserMapper;

    /**
     * 登陆验证时，通过username获取用户的所有权限信息
     * 并返回UserDetails放到spring的全局缓存SecurityContextHolder中，以供授权器使用
     */

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//            获取当前的用户

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        MyUserDetails userDetails= (MyUserDetails) authentication.getPrincipal();
        List<GrantedAuthority> grantedAuthorities=AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_admin");
        return new User(username, passwordEncoder.encode("123456"), grantedAuthorities);

    }
}
