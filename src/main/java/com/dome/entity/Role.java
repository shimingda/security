package com.dome.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "t_role")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id",nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;
}
