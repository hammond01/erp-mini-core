package com.erpmini.erp_core.modules.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PermissionDto {
    private Long id;
    private String name;
    private String description;
}