package com.erpmini.erp_core.modules.auth.entity;

import com.erpmini.erp_core.shared.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Getter @Setter
public class AuditLog extends BaseEntity {
    private String username;
    private String action;   
    private String detail;
    private LocalDateTime actionAt = LocalDateTime.now();
    private String ipAddress;
}