package com.liquibase.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@ToString(callSuper = true)
@Setter
@Getter
@Entity
@Table(name = "note")
public class Note extends BaseEntity {

    @NotBlank
    @Column(name = "Title")
    private String title;

    @NotBlank
    @Column(name = "Content")
    private String content;


}