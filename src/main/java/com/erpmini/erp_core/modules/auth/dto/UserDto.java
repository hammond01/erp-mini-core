package com.erpmini.erp_core.modules.auth.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private Boolean active;
    private Set<String> roles;
}
