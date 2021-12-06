package com.liquibase.entities;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString(callSuper = true)
@Entity
@Table(name = "book")
public class Book extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "author")
    private Author author;

    protected Book() {
        // for JPA
    }

    public Book(String name, Author author) {
        this.name = name;
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                ", name='" + name + '\'' +
                ", author=" + author +
                '}';
    }
}
