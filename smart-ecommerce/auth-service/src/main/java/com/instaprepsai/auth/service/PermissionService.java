package com.instaprepsai.auth.service;

import com.instaprepsai.auth.data.model.Permission;
import com.instaprepsai.auth.data.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    public Optional<Permission> getPermissionById(Long id) {
        return permissionRepository.findById(id);
    }

    public Permission createPermission(Permission permission) {
        return permissionRepository.save(permission);
    }

    public Permission updatePermission(Long id, Permission updatedPermission) {
        return permissionRepository.findById(id).map(permission -> {
            permission.setPermissionName(updatedPermission.getPermissionName());
            permission.setDescription(updatedPermission.getDescription());
            return permissionRepository.save(permission);
        }).orElseThrow(() -> new RuntimeException("Permission not found"));
    }

    public void deletePermission(Long id) {
        permissionRepository.deleteById(id);
    }
}