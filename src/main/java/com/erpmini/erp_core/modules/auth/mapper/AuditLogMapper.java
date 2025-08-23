package com.erpmini.erp_core.modules.auth.mapper;

import com.erpmini.erp_core.modules.auth.dto.AuditLogDto;
import com.erpmini.erp_core.modules.auth.entity.AuditLog;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuditLogMapper {
    AuditLogDto toDto(AuditLog log);
}