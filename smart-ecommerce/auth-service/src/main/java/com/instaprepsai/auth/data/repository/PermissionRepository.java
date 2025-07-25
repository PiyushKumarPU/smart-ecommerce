package com.instaprepsai.auth.data.repository;

import com.instaprepsai.auth.data.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    // Custom query methods can be added here if needed
    Permission findByPermissionName(String permissionName);
}