package com.instaprepsai.auth.service;

import com.instaprepsai.auth.data.model.RolePermission;
import com.instaprepsai.auth.data.repository.RolePermissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RolePermissionService {

    private final RolePermissionRepository rolePermissionRepository;

    public List<RolePermission> getAllRolePermissions() {
        return rolePermissionRepository.findAll();
    }

    public Optional<RolePermission> getRolePermissionById(Long id) {
        return rolePermissionRepository.findById(id);
    }

    public RolePermission createRolePermission(RolePermission rolePermission) {
        return rolePermissionRepository.save(rolePermission);
    }

    public RolePermission updateRolePermission(Long id, RolePermission updatedRolePermission) {
        return rolePermissionRepository.findById(id).map(rolePermission -> {
            rolePermission.setRole(updatedRolePermission.getRole());
            rolePermission.setPermission(updatedRolePermission.getPermission());
            return rolePermissionRepository.save(rolePermission);
        }).orElseThrow(() -> new RuntimeException("RolePermission not found"));
    }

    public void deleteRolePermission(Long id) {
        rolePermissionRepository.deleteById(id);
    }
}