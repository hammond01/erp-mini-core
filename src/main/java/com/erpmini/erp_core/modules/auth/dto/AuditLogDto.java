package com.erpmini.erp_core.modules.auth.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class AuditLogDto {
    private Long id;
    private String username;
    private String action;
    private String detail;
    private LocalDateTime actionAt;
    private String ipAddress;
}