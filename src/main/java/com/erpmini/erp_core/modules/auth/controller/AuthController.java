package com.erpmini.erp_core.modules.auth.controller;

import com.erpmini.erp_core.modules.auth.dto.AuthRequest;
import com.erpmini.erp_core.modules.auth.dto.AuthResponse;
import com.erpmini.erp_core.modules.auth.dto.RegisterRequest;
import com.erpmini.erp_core.modules.auth.entity.User;
import com.erpmini.erp_core.modules.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/me")
    public ResponseEntity<User> me() {
        return ResponseEntity.ok(authService.getCurrentUser());
    }
}
