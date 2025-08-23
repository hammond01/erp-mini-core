package com.erpmini.erp_core.modules.auth.mapper;

import com.erpmini.erp_core.modules.auth.dto.PermissionDto;
import com.erpmini.erp_core.modules.auth.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionDto toDto(Permission permission);
}
