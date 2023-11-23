package com.liquibase.repositories;

import com.liquibase.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EmployeeDao extends JpaRepository<Employee, Long> {

    @Query("select u from #{#entityName} u where u.email = ?1")
    Optional<Employee> findByEmail(String email);

}
