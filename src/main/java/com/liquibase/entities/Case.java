package com.liquibase.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@Entity
@Table(name = "casea")
public class Case extends AbstractEntity {

    @Column(name = "name", unique = true)
    private String name;
}
