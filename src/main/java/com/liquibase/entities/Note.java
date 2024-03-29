package com.liquibase.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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