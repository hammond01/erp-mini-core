package com.erpmini.erp_core.config;

import com.erpmini.erp_core.modules.auth.entity.Permission;
import com.erpmini.erp_core.modules.auth.entity.Role;
import com.erpmini.erp_core.modules.auth.entity.User;
import com.erpmini.erp_core.modules.auth.repository.PermissionRepository;
import com.erpmini.erp_core.modules.auth.repository.RoleRepository;
import com.erpmini.erp_core.modules.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PermissionRepository permissionRepo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) {
        if (userRepo.count() == 0) {
            Permission p1 = new Permission(); p1.setName("USER_MANAGE"); p1.setDescription("Manage users");
            Permission p2 = new Permission(); p2.setName("ROLE_MANAGE"); p2.setDescription("Manage roles");
            Permission p3 = new Permission(); p3.setName("PERMISSION_MANAGE"); p3.setDescription("Manage permissions");
            permissionRepo.saveAll(List.of(p1, p2, p3));

            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            adminRole.setPermissions(new HashSet<>(permissionRepo.findAll()));
            roleRepo.save(adminRole);

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(encoder.encode("123456"));
            admin.setFullName("System Administrator");
            admin.setEmail("admin@erpmini.com");
            admin.setActive(true);
            admin.setRoles(Set.of(adminRole));

            userRepo.save(admin);

            System.out.println("✅ Seeded default admin user (admin / 123456)");
        }
    }
}