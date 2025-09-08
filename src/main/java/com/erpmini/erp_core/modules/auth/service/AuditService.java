package com.erpmini.erp_core.modules.auth.service;

import com.erpmini.erp_core.modules.auth.entity.AuditLog;
import com.erpmini.erp_core.modules.auth.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

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
            ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = attrs.getRequest();
            String ip = request.getHeader("X-Forwarded-For");
            if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            return ip;
        } catch (Exception e) {
            return "UNKNOWN";
        }
    }
}
