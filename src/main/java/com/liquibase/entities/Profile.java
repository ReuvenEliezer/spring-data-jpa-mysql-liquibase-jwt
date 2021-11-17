package com.liquibase.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "profile")
@Getter
@Setter
public class Profile extends AbstractEntity {

    @Column (name = "first_name")
    private String firstName;
    @Column
    private String photo;
}
