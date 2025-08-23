package com.erpmini.erp_core.modules.auth.security;

import com.erpmini.erp_core.modules.auth.annotation.HasPermission;
import com.erpmini.erp_core.modules.auth.entity.User;
import com.erpmini.erp_core.modules.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class PermissionAspect {

    private final UserRepository userRepo;

    @Before("@annotation(hasPermission)")
    public void checkPermission(HasPermission hasPermission) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new RuntimeException("Unauthorized");
        }

        String username = auth.getName();
        User user = userRepo.findByUsername(username).orElseThrow();

        String requiredPerm = hasPermission.value();

        boolean hasPerm = user.getRoles().stream()
                .flatMap(r -> r.getPermissions().stream())
                .anyMatch(p -> p.getName().equals(requiredPerm));

        if (!hasPerm) {
            throw new RuntimeException("Access denied: missing permission " + requiredPerm);
        }
    }
}