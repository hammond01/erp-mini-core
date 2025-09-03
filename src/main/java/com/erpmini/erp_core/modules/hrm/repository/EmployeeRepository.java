package com.erpmini.erp_core.modules.hrm.repository;

import com.erpmini.erp_core.modules.hrm.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {}
