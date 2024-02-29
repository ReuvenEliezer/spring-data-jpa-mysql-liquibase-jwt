package com.liquibase.services.web;

import com.liquibase.client_entities.EmployeeViewModel;
import com.liquibase.entities.Employee;
import com.liquibase.services.transactional.TransactionalOperationsUtil;
import com.liquibase.services.web.convert.EntityVmConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class EmployeeWebService extends AbstractEntityWebService<Employee, EmployeeViewModel, Long> {

    EmployeeWebService(EntityVmConverter<Employee, EmployeeViewModel> converter,
                       JpaRepository<Employee, Long> jpaRepository,
                       TransactionalOperationsUtil transactionalOperationsUtil) {
        super(converter, jpaRepository, transactionalOperationsUtil);
    }
}
