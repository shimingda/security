package com.dome.service;

import com.dome.entity.Role;
import org.springframework.stereotype.Service;

public interface RoleService {
    Role findById(Long id);
}
