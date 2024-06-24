package com.hildabur.bambikbaby.dao;

import com.hildabur.bambikbaby.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    UserRole findByName(String name);
    boolean existsByName(String name);
}
