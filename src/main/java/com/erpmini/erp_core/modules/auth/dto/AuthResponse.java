package com.erpmini.erp_core.modules.auth.dto;

import lombok.*;

import java.util.Set;

@Getter @Setter
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String username;
    private Set<String> roles;
}