package com.liquibase.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "case")
@Getter
@Setter
public class Case extends AbstractEntity {

    @Column(unique = true)
    private String name;
}
