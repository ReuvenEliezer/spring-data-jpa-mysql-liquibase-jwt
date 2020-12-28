package com.liquibase.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
class CourseRatingKey implements Serializable {


    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "course_id")
    private Long courseId;

    public Long getStudentId() {
        return studentId;
    }

    public CourseRatingKey() {
    }

    public CourseRatingKey(Long studentId, Long courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public Long getCourseId() {
        return courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseRatingKey that = (CourseRatingKey) o;
        return Objects.equals(studentId, that.studentId) && Objects.equals(courseId, that.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseId);
    }

    @Override
    public String toString() {
        return "CourseRatingKey{" +
                "studentId=" + studentId +
                ", courseId=" + courseId +
                '}';
    }
}