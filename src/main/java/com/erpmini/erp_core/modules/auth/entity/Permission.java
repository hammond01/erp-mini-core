package com.erpmini.erp_core.modules.auth.entity;

import com.erpmini.erp_core.shared.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "permissions")
@Getter @Setter
public class Permission extends BaseEntity {
    @Column(nullable = false, unique = true, length = 100)
    private String name;   // ex: USER_CREATE, ORDER_VIEW
    private String description;
}