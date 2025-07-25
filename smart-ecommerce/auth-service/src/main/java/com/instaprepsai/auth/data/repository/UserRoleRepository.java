package com.instaprepsai.auth.data.repository;

import com.instaprepsai.auth.data.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    // Custom query methods can be added here if needed
}