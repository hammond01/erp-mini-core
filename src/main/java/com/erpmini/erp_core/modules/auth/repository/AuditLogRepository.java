package com.erpmini.erp_core.modules.auth.repository;

import com.erpmini.erp_core.modules.auth.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {}