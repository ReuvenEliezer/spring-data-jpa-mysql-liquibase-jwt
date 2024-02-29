package com.liquibase.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "author")
public class Author extends AbstractEntity {

    /**
     * Explaining strategies: https://thoughts-on-java.org/jpa-generate-primary-keys/
     */

    @Column(nullable = false, unique = true)
    private String name;

    protected Author() {
        // for JPA
    }


    public Author(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Author{" +
                ", name='" + name + '\'' +
                '}';
    }
}
