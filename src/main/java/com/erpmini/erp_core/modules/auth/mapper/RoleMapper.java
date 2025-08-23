package com.erpmini.erp_core.modules.auth.mapper;

import com.erpmini.erp_core.modules.auth.dto.RoleDto;
import com.erpmini.erp_core.modules.auth.entity.Permission;
import com.erpmini.erp_core.modules.auth.entity.Role;
import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "permissions", source = "permissions", qualifiedByName = "mapPermissions")
    RoleDto toDto(Role role);

    @Named("mapPermissions")
    static Set<String> mapPermissions(Set<Permission> permissions) {
        if (permissions == null) return Set.of();
        return permissions.stream().map(Permission::getName).collect(Collectors.toSet());
    }
}