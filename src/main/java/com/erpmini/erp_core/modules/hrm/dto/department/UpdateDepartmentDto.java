package com.erpmini.erp_core.modules.hrm.dto.department;

import lombok.Data;

@Data
public class UpdateDepartmentDto {
    private String name;
    private Long parentId;
}
