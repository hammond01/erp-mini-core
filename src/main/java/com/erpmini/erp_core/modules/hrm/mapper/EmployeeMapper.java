package com.erpmini.erp_core.modules.hrm.mapper;

import com.erpmini.erp_core.modules.hrm.dto.employee.*;
import com.erpmini.erp_core.modules.hrm.entity.Employee;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    // Create → Entity
    @Mapping(target = "id", ignore = true) // id auto gen
    @Mapping(source = "departmentId", target = "department.id")
    @Mapping(source = "positionId", target = "position.id")
    @Mapping(source = "userId", target = "user.id")
    Employee toEntity(CreateEmployeeDto dto);

    // Update DTO → Entity (update existing employee)
    /**
     * @param dto - UpdateEmployeeDto
     * @param employee - Employee
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "departmentId", target = "department.id")
    @Mapping(source = "positionId", target = "position.id")
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateEntity(UpdateEmployeeDto dto, @MappingTarget Employee employee);

    // Entity → Get DTO 
    @Mapping(source = "department.name", target = "departmentName")
    @Mapping(source = "position.name", target = "positionName")
    @Mapping(source = "user.username", target = "username")
    GetEmployeeDto toGetDto(Employee employee);
}
