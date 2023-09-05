package com.liquibase.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Table(name = "cases")
public class Case extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "is_deleted")
    private boolean isDeleted;
}
