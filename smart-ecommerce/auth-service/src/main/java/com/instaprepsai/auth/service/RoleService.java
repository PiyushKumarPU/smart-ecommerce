package com.instaprepsai.auth.service;

import com.instaprepsai.auth.data.model.Role;
import com.instaprepsai.auth.data.model.RoleEnum;
import com.instaprepsai.auth.data.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleService {

    private final RoleRepository roleRepository;


    public List<Role> getByRoleNames(List<RoleEnum> roleEnums) {
        return roleEnums.stream().map(role -> roleRepository.findByRoleName(role.getName())).toList();
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Optional<Role> getRoleById(Long id) {
        return roleRepository.findById(id);
    }

    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    public Role updateRole(Long id, Role updatedRole) {
        return roleRepository.findById(id).map(role -> {
            role.setRoleName(updatedRole.getRoleName());
            role.setDescription(updatedRole.getDescription());
            return roleRepository.save(role);
        }).orElseThrow(() -> new RuntimeException("Role not found"));
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}