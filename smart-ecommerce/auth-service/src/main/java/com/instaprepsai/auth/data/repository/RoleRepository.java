package com.instaprepsai.auth.data.repository;

import com.instaprepsai.auth.data.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    // Custom query methods can be added here if needed
    Role findByRoleName(String roleName);
}