package com.dome.authenticate;


import com.dome.entity.MyUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class MyCustomUserService implements UserDetailsService {

//    @Autowired
//    private SysUserMapper sysUserMapper;

    /**
     * 登陆验证时，通过username获取用户的所有权限信息
     * 并返回UserDetails放到spring的全局缓存SecurityContextHolder中，以供授权器使用
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        MyUserDetails userDetails= (MyUserDetails) authentication.getPrincipal();
        MyUserDetails userDetails= new MyUserDetails();
        userDetails.setUsername(username);
        return userDetails;
    }
}
