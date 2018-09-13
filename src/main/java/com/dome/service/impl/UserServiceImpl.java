package com.dome.service.impl;

import com.dome.entity.User;
import com.dome.repository.UserRepository;
import com.dome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repository;

    @Override
    public User getUserByName(String username) {
        User user=repository.findByUsername(username);
        return user;
    }
}
