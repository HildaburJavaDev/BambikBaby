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

    @Column (nullable = false, name = "role_name")
    private String roleName;
}
