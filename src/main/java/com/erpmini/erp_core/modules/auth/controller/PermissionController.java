package com.erpmini.erp_core.modules.auth.controller;

import com.erpmini.erp_core.modules.auth.dto.PermissionDto;
import com.erpmini.erp_core.modules.auth.entity.Permission;
import com.erpmini.erp_core.modules.auth.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/permissions")
@RequiredArgsConstructor
public class PermissionController {
    private final PermissionService permissionService;

    @GetMapping
    public List<PermissionDto> getAll() {
        return permissionService.getAll();
    }

    @PostMapping
    public PermissionDto create(@RequestBody Permission permission) {
        return permissionService.create(permission);
    }
}
