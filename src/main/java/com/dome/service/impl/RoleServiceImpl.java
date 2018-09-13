package com.dome.service.impl;

import com.dome.entity.Role;
import com.dome.repository.RoleRepository;
import com.dome.service.RoleService;
import com.dome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Role findById(Long id) {
            Role role=roleRepository.findById(id);
        return role;
    }
}
