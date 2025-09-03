package com.erpmini.erp_core.modules.auth.service;

import com.erpmini.erp_core.modules.auth.entity.AuditLog;
import com.erpmini.erp_core.modules.auth.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    @Transactional
    public void logAction(String username, String action, String entityType, String entityId, String detail, String ipAddress) {
        try {
            AuditLog auditLog = new AuditLog();
            auditLog.setUsername(username);
            auditLog.setAction(action);
            auditLog.setDetail(String.format("%s %s: %s", action, entityType, detail));
            auditLog.setIpAddress(ipAddress);
            auditLog.setActionAt(LocalDateTime.now());

            auditLogRepository.save(auditLog);

            log.info("Audit logged: User '{}' performed '{}' on {} with id '{}'", username, action, entityType, entityId);
        } catch (Exception e) {
            log.error("Failed to save audit log for user '{}' action '{}': {}", username, action, e.getMessage(), e);
        }
    }

    @Transactional
    public void logDatabaseOperation(String operation, String tableName, String details) {
        try {
            AuditLog auditLog = new AuditLog();
            auditLog.setUsername("SYSTEM");
            auditLog.setAction("DATABASE_" + operation);
            auditLog.setDetail(String.format("Database operation: %s on table %s - %s", operation, tableName, details));
            auditLog.setIpAddress("SYSTEM");
            auditLog.setActionAt(LocalDateTime.now());

            auditLogRepository.save(auditLog);

            log.debug("Database operation logged: {} on table {}", operation, tableName);
        } catch (Exception e) {
            log.error("Failed to log database operation: {}", e.getMessage(), e);
        }
    }

    public String getCurrentUsername() {
        try {
            return org.springframework.security.core.context.SecurityContextHolder
                    .getContext().getAuthentication().getName();
        } catch (Exception e) {
            return "SYSTEM";
        }
    }

    public String getClientIpAddress() {
        try {
            // This would need to be implemented based on your request handling
            return "UNKNOWN";
        } catch (Exception e) {
            return "UNKNOWN";
        }
    }
}
