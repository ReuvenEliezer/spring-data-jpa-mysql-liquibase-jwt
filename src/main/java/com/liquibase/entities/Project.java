package com.liquibase.entities;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "project")
public class Project extends AbstractEntity {

    @Column(name = "title")
    private String title;


    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "projects")
    private Set<Employee> employees = new HashSet<>();

    public void addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.getProjects().add(this);
    }

    public void removeEmployee(Employee employee) {
        this.employees.remove(employee);
        employee.getProjects().remove(this);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }


//    @Override
//    public String toString() {
//        return "Project{" +
//                "title='" + title + '\'' +
//                ", employees=" + employees +
//                '}';
//    }
}