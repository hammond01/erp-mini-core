package com.erpmini.erp_core.modules.hrm.dto.department;

import lombok.Data;

@Data
public class GetDepartmentDto {
    private Long id;
    private String code;
    private String name;
    private String parentName;
}
