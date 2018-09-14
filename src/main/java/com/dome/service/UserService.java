package com.dome.service;

import com.dome.entity.SysUser;

/**
 *
 */
public interface UserService {
     SysUser getUserByName(String username);

     void save(SysUser user);
}
