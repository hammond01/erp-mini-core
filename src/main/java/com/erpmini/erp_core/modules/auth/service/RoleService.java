package com.erpmini.erp_core.modules.auth.service;

import com.erpmini.erp_core.modules.auth.dto.RoleDto;
import com.erpmini.erp_core.modules.auth.entity.Permission;
import com.erpmini.erp_core.modules.auth.entity.Role;
import com.erpmini.erp_core.modules.auth.mapper.RoleMapper;
import com.erpmini.erp_core.modules.auth.repository.PermissionRepository;
import com.erpmini.erp_core.modules.auth.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepo;
    private final PermissionRepository permissionRepo;
    private final RoleMapper roleMapper;

    public List<RoleDto> getAll() {
        return roleRepo.findAll().stream().map(roleMapper::toDto).toList();
    }

    public RoleDto create(Role role) {
        return roleMapper.toDto(roleRepo.save(role));
    }

    @Transactional
    public void assignPermission(Long roleId, String permissionName) {
        Role role = roleRepo.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        Permission p = permissionRepo.findByName(permissionName)
                .orElseThrow(() -> new RuntimeException("Permission not found"));
        role.getPermissions().add(p);
        roleRepo.save(role);
    }
}