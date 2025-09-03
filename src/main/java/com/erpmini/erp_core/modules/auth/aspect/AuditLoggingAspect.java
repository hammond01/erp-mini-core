package com.erpmini.erp_core.modules.auth.aspect;

import com.erpmini.erp_core.modules.auth.annotation.AuditLoggable;
import com.erpmini.erp_core.modules.auth.service.AuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AuditLoggingAspect {

    private final AuditService auditService;

    @Pointcut("@annotation(com.erpmini.erp_core.modules.auth.annotation.AuditLoggable)")
    public void auditLoggableMethods() {}

    @Around("auditLoggableMethods()")
    public Object logAudit(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        AuditLoggable annotation = method.getAnnotation(AuditLoggable.class);

        String entityType = annotation.entityType();
        String action = annotation.action();

        // If entityType is not specified, try to infer from method name
        if (entityType.isEmpty()) {
            entityType = inferEntityType(signature.getDeclaringType().getSimpleName());
        }

        // If action is not specified, infer from method name
        if (action.isEmpty()) {
            action = inferAction(signature.getName());
        }

        String username = auditService.getCurrentUsername();
        String ipAddress = auditService.getClientIpAddress();

        // Extract entity ID from method parameters if possible
        String entityId = extractEntityId(joinPoint.getArgs());

        try {
            log.debug("Starting audited operation: {} on {} by user {}", action, entityType, username);

            Object result = joinPoint.proceed();

            // Log successful operation
            auditService.logAction(username, action, entityType, entityId,
                    String.format("Method: %s", method.getName()), ipAddress);

            log.debug("Completed audited operation: {} on {} by user {}", action, entityType, username);

            return result;

        } catch (Exception e) {
            // Log failed operation
            auditService.logAction(username, "FAILED_" + action, entityType, entityId,
                    String.format("Method: %s - Error: %s", method.getName(), e.getMessage()), ipAddress);

            log.error("Failed audited operation: {} on {} by user {} - Error: {}", action, entityType, username, e.getMessage());

            throw e;
        }
    }

    private String inferEntityType(String className) {
        // Remove "Service" or "Controller" suffix
        if (className.endsWith("Service")) {
            return className.substring(0, className.length() - 7);
        } else if (className.endsWith("Controller")) {
            return className.substring(0, className.length() - 10);
        }
        return className;
    }

    private String inferAction(String methodName) {
        if (methodName.startsWith("create") || methodName.startsWith("save") || methodName.startsWith("add")) {
            return "CREATE";
        } else if (methodName.startsWith("update") || methodName.startsWith("edit") || methodName.startsWith("modify")) {
            return "UPDATE";
        } else if (methodName.startsWith("delete") || methodName.startsWith("remove")) {
            return "DELETE";
        } else if (methodName.startsWith("get") || methodName.startsWith("find") || methodName.startsWith("read")) {
            return "READ";
        }
        return "EXECUTE";
    }

    private String extractEntityId(Object[] args) {
        // Try to find an ID parameter (Long, Integer, String)
        for (Object arg : args) {
            if (arg instanceof Long || arg instanceof Integer) {
                return arg.toString();
            } else if (arg instanceof String && ((String) arg).matches("\\d+")) {
                return (String) arg;
            }
        }
        return "UNKNOWN";
    }
}
