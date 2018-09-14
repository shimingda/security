package com.dome.service.impl;

import com.dome.entity.SysUser;
import com.dome.repository.UserRepository;
import com.dome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Override
    public SysUser getUserByName(String username) {
        SysUser user=repository.findByUsername(username);
        return user;
    }

    @Override
    public void save(SysUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
    }
}
