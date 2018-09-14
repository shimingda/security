package com.dome.authenticate;

import com.dome.entity.Role;
import com.dome.entity.SysUser;
import com.dome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;

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
    @Autowired
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) {
//            获取当前的用户
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            SysUser user=null;
            if (null!=authentication){
               user= (SysUser) authentication.getPrincipal();
            }
            if(null==user){
                user=userService.getUserByName(username);
                if (null==user) {
                    throw new BadCredentialsException("用户不存在");
                }
            }
            List<String> roles=user.getRoles().stream().map(x->{return  x.getName();}).collect(Collectors.toList());
            String[]  roleStrs=new String[roles.size()];
            roles.toArray(roleStrs);
            return new User(username,passwordEncoder.encode(user.getPassword()),AuthorityUtils.createAuthorityList(roleStrs));
        }
}
