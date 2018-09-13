package com.dome.repository;

import com.dome.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role,String> {

    Role findByName(String name);

    Role findById(Long id);
}
