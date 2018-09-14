package com.dome.authenticate;

import com.dome.entity.SysUser;
import com.dome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class MyCustomUserService implements UserDetailsService {
    /**
     * 登陆验证时，通过username获取用户的所有权限信息
     * 并返回UserDetails放到spring的全局缓存SecurityContextHolder中，以供授权器使用
     */
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
            return user;
        }
}
