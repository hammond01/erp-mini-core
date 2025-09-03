package com.erpmini.erp_core.modules.hrm.entity;

import com.erpmini.erp_core.modules.auth.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "employees")
@Getter @Setter
public class Employee {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code; // EMP001

    @Column(nullable = false)
    private String fullName;

    private LocalDate dob;
    private String gender;
    private String phone;
    private String email;

    @ManyToOne @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne @JoinColumn(name = "position_id")
    private Position position;

    @OneToOne @JoinColumn(name = "user_id")
    private User user; // mapping với module Auth
}
