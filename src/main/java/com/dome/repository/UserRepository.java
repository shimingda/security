package com.dome.repository;

import com.dome.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<SysUser,Long> {
    SysUser findByUsername(String username);

    Long countByUsername(String username);
}