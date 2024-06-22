package com.hildabur.bambikbaby.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_roles")
@Data
public class UserRole {
    @Id
    @GeneratedValue
    private int id;

    @Column (name = "role_name", nullable = false)
    private String roleName;
}
