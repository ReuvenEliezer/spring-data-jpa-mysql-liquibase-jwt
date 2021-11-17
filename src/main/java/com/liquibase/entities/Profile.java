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
@Table(name = "profile")
public class Profile extends AbstractEntity {

    @Column (name = "first_name")
    private String firstName;
    @Column(name = "photo")
    private String photo;
}
