package com.erpmini.erp_core.modules.auth.dto;

import lombok.*;

@Getter
@Setter
public class LoginRequest {
    private String username;
    private String password;
}
