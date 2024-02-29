package com.liquibase.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employee")
public class Employee extends AbstractEntity {

    @Email
    @NotEmpty
    @Column(name = "email", unique = true)
    private String email;
    @NotEmpty
    @Column(name = "password")
    private String password;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    @ManyToMany(fetch = FetchType.EAGER)//(cascade = {CascadeType.ALL})
    @JoinTable(name = "employee_project",
            joinColumns = {@JoinColumn(name = "employee_id")}, inverseJoinColumns = {@JoinColumn(name = "project_id")})
    private Set<Project> projects = new HashSet<>();


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public void addProject(Project project) {
        this.projects.add(project);
        project.getEmployees().add(this);
    }

    public void removeProject(Project project) {
        this.projects.remove(project);
        project.getEmployees().remove(this);
    }

//    @Override
//    public String toString() {
//        return "Employee{" +
//                ", firstName='" + firstName + '\'' +
//                ", lastName='" + lastName + '\'' +
//                ", projects=" + projects +
//                '}';
//    }
}