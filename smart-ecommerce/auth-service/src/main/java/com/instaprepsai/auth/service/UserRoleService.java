package com.instaprepsai.auth.service;

import com.instaprepsai.auth.data.model.UserRole;
import com.instaprepsai.auth.data.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public List<UserRole> getAllUserRoles() {
        return userRoleRepository.findAll();
    }

    public Optional<UserRole> getUserRoleById(Long id) {
        return userRoleRepository.findById(id);
    }

    public UserRole createUserRole(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }

    public UserRole updateUserRole(Long id, UserRole updatedUserRole) {
        return userRoleRepository.findById(id).map(userRole -> {
            userRole.setUser(updatedUserRole.getUser());
            userRole.setRole(updatedUserRole.getRole());
            return userRoleRepository.save(userRole);
        }).orElseThrow(() -> new RuntimeException("UserRole not found"));
    }
}