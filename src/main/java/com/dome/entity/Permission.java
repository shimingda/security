package com.dome.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "t_permission")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id",nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;
}
