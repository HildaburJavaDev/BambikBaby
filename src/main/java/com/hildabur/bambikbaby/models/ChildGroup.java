package com.hildabur.bambikbaby.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "child_groups")
@Data
public class ChildGroup {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "chief_id")
    private User chief;

    @Column(name = "title", nullable = false)
    private String title;

}
