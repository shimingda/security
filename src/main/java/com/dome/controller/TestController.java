package com.dome.controller;

import com.dome.entity.Permission;
import com.dome.entity.Role;
import com.dome.entity.SysUser;
import com.dome.service.PermissionService;
import com.dome.service.RoleService;
import com.dome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;
   @RequestMapping("/user")
    public String user() {
       SysUser user= userService.getUserByName("test");
       System.out.println(user.getId()+"-----"+user.getPassword());
       user.getRoles();
       return user.getPassword();
    }
    @RequestMapping("/role")
    public String role() {
       Role role=roleService.findById(new Long(1));
        System.out.println(role.getName());
        return role.getName();
    }
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
