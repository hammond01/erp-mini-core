package com.erpmini.erp_core.modules.auth.service;

import com.erpmini.erp_core.modules.auth.dto.*;
import com.erpmini.erp_core.modules.auth.entity.*;
import com.erpmini.erp_core.modules.auth.repository.*;
import com.erpmini.erp_core.modules.auth.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuditLogRepository auditRepo;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Role USER not found"));

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRoles(Collections.singleton(userRole));

        userRepository.save(user);

        String token = jwtProvider.generateToken(user);
        return new AuthResponse(
                token,
                user.getUsername(),
                user.getRoles().stream().map(Role::getName).collect(Collectors.toSet())
        );
    }

    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtProvider.generateToken(user);
        return new AuthResponse(
                token,
                user.getUsername(),
                user.getRoles().stream().map(Role::getName).collect(Collectors.toSet())
        );
    }

    private void saveLog(String username, String action, String detail, String ip) {
        AuditLog log = new AuditLog();
        log.setUsername(username);
        log.setAction(action);
        log.setDetail(detail);
        log.setIpAddress(ip);
        log.setActionAt(LocalDateTime.now());
        auditRepo.save(log);
    }


    public User getCurrentUser() {
        // lấy từ SecurityContextHolder
        return userRepository.findByUsername(
                org.springframework.security.core.context.SecurityContextHolder
                        .getContext().getAuthentication().getName()
        ).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
