package com.liquibase.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "course_rating")
public class CourseRating implements Serializable {

    @EmbeddedId
    private CourseRatingKey id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "rating")
    private Double rating;

    public CourseRating(Student student, Course course, Double rating) {
        this.student = student;
        this.course = course;
        this.rating = rating;
        id = new CourseRatingKey(student.getId(), course.getId());
    }

    public CourseRating() {
        id = new CourseRatingKey();
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public CourseRatingKey getId() {
        return id;
    }

//    public CourseRating() {
//        this.id = new CourseRatingKey();
//    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseRating that = (CourseRating) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CourseRating{" +
                "id=" + id +
                ", student=" + student +
                ", course=" + course +
                ", rating=" + rating +
                '}';
    }
}
