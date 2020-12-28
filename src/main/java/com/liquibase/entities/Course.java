package com.liquibase.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "course")
public class Course extends AbstractEntity{

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "course")
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

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                '}';
    }
}
