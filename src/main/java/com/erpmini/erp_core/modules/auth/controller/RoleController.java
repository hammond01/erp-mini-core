package com.erpmini.erp_core.modules.auth.controller;

import com.erpmini.erp_core.modules.auth.dto.RoleDto;
import com.erpmini.erp_core.modules.auth.entity.Role;
import com.erpmini.erp_core.modules.auth.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping
    public List<RoleDto> getAll() {
        return roleService.getAll();
    }

    @PostMapping
    public RoleDto create(@RequestBody Role role) {
        return roleService.create(role);
    }

    @PostMapping("/{id}/assign-permission")
    public String assignPermission(@PathVariable Long id, @RequestBody Map<String, String> body) {
        roleService.assignPermission(id, body.get("permission"));
        return "Permission assigned";
    }
}