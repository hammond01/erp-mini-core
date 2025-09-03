package com.erpmini.erp_core.modules.hrm.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "positions")
@Getter @Setter
public class Position {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code; // DEV, TEST, MGR

    @Column(nullable = false)
    private String name;

    private int level; // 1=staff, 2=lead, 3=manager...
}
