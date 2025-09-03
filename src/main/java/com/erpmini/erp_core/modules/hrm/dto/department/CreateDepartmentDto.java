package com.erpmini.erp_core.modules.hrm.dto.department;

import lombok.Data;

@Data
public class CreateDepartmentDto {
    private String code;
    private String name;
    private Long parentId;
}

