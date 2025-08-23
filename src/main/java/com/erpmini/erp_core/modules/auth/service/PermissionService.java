package com.erpmini.erp_core.modules.auth.service;

import com.erpmini.erp_core.modules.auth.dto.PermissionDto;
import com.erpmini.erp_core.modules.auth.entity.Permission;
import com.erpmini.erp_core.modules.auth.mapper.PermissionMapper;
import com.erpmini.erp_core.modules.auth.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionService {
    private final PermissionRepository permissionRepo;
    private final PermissionMapper permissionMapper;

    public List<PermissionDto> getAll() {
        return permissionRepo.findAll().stream().map(permissionMapper::toDto).toList();
    }

    public PermissionDto create(Permission permission) {
        return permissionMapper.toDto(permissionRepo.save(permission));
    }
}
