//package com.liquibase.repositories;
//
//import com.liquibase.entities.EmployeeProject;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import java.util.List;
//
//public interface EmployeeProjectDao extends JpaRepository<EmployeeProject, Integer> {
//
//    @Query("select u from #{#entityName} u where u.employeeId = ?1")
//    List<EmployeeProject> getAllByEmployee(Integer employeeId);
//
//    @Query("select u from #{#entityName} u where u.projectId = ?1")
//    List<EmployeeProject> getAllByProject(Integer projectId);
//
//    @Query("select u from #{#entityName} u where u.employeeId = ?1 and u.projectId = ?2")
//    EmployeeProject getDosingChannelRecipe(Integer employeeId, Integer projectId);
//
//}
