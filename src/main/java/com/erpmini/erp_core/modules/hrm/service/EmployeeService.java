package com.erpmini.erp_core.modules.hrm.service;

import com.erpmini.erp_core.modules.hrm.dto.employee.CreateEmployeeDto;
import com.erpmini.erp_core.modules.hrm.dto.employee.UpdateEmployeeDto;
import com.erpmini.erp_core.modules.hrm.dto.employee.GetEmployeeDto;
import com.erpmini.erp_core.modules.hrm.entity.Employee;
import com.erpmini.erp_core.modules.hrm.mapper.EmployeeMapper;
import com.erpmini.erp_core.modules.hrm.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository repo;
    private final EmployeeMapper mapper;

    public List<GetEmployeeDto> getAll() {
        return repo.findAll().stream().map(mapper::toGetDto).toList();
    }

    public GetEmployeeDto getById(Long id) {
        return repo.findById(id)
                .map(mapper::toGetDto)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    public GetEmployeeDto create(CreateEmployeeDto dto) {
        Employee employee = mapper.toEntity(dto);
        return mapper.toGetDto(repo.save(employee));
    }

    public GetEmployeeDto update(Long id, UpdateEmployeeDto dto) {
        Employee employee = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        mapper.updateEntity(dto, employee);
        return mapper.toGetDto(repo.save(employee));
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Employee not found");
        }
        repo.deleteById(id);
    }
}
