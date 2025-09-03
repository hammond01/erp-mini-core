package com.erpmini.erp_core.modules.hrm.dto.employee;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateEmployeeDto {
    private String code;
    private String fullName;
    private LocalDate dob;
    private String gender;
    private String phone;
    private String email;
    private Long departmentId;
    private Long positionId;
    private Long userId; 
}
