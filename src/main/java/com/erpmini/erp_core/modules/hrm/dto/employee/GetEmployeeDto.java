package com.erpmini.erp_core.modules.hrm.dto.employee;

import lombok.Data;

@Data
public class GetEmployeeDto {
    private Long id;
    private String code;
    private String fullName;
    private String phone;
    private String email;
    private String departmentName;
    private String positionName;
    private String username; 
}
