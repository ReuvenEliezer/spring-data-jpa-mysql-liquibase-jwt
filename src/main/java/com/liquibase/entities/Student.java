package com.liquibase.entities;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "student")
@Data
public class Student extends AbstractEntity {

    @Column(name = "name")
    private String name;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "student")
    private Set<CourseRating> ratings = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CourseRating> getRatings() {
        return ratings;
    }

    public void setRatings(Set<CourseRating> ratings) {
        this.ratings = ratings;
    }
//
//    @Override
//    public String toString() {
//        return "Student{" +
//                "name='" + name + '\'' +
//                '}';
//    }
}
