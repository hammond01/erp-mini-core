package com.erpmini.erp_core.modules.auth.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RoleDto {
    private Long id;
    private String name;
    private Set<String> permissions;
}