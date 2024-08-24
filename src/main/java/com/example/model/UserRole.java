package com.example.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "users_roles")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "role_name")
    private String roleName;

}
