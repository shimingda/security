package com.dome.controller;

import com.dome.entity.Permission;
import com.dome.entity.Role;
import com.dome.entity.SysUser;
import com.dome.service.PermissionService;
import com.dome.service.RoleService;
import com.dome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @PostAuthorize("returnObject.username==principal.username")//过滤返回值结果
    @RequestMapping("/save")
    public SysUser user() {
       SysUser user= userService.getUserByName("admin");
       user.setPassword("123");
       System.out.println(user.getId()+"-----"+user.getPassword());
        userService.save(user);
       return user;
    }

//    @PreAuthorize("hasAnyRole('ROLE_admin')")//验证角色
    @RequestMapping("/role")
    public String role() {
       Role role=roleService.findById(new Long(1));
        System.out.println(role.getName());
        return role.getName();
    }
    @PreAuthorize("hasAnyRole('ROLE_Admin')")
    @RequestMapping("/permission")
    public String permission() {
        Permission permission=permissionService.findById(new Long(1001));
        return permission.getName();
    }
    @RequestMapping("/del")
    public String del() {
        return "del";
    }
    @RequestMapping("/update")
    public String update() {
        return "update";
    }
}
