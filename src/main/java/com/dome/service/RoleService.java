package com.dome.service;

import com.dome.entity.Role;
import org.springframework.security.access.annotation.Secured;

public interface RoleService {

    @Secured("ROLE_admin")
    Role findById(Long id);
}
