package com.erpmini.erp_core.modules.hrm.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "departments")
@Getter
@Setter
public class Department {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code; // VD: HR, IT, ACC

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Department parent; // cây phòng ban

    @OneToMany(mappedBy = "parent")
    private Set<Department> children = new HashSet<>();
}
