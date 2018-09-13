package com.dome.service.impl;

import com.dome.entity.Permission;
import com.dome.repository.PermissionRepository;
import com.dome.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImple implements PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;
    @Override
    public Permission findById(Long id) {
        Permission permission=permissionRepository.findById(id);
        return permission;
    }
}
