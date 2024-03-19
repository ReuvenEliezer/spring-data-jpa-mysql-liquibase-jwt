package com.liquibase.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Table(name = "profile")
public class Profile extends BaseEntity {

    @Column(name = "name")
    private String name;
    @Column(name = "photo")
    private String photo;
}
