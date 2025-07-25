package com.instaprepsai.auth.data.repository;

import com.instaprepsai.auth.data.model.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {
    // Custom query methods can be added here if needed
}