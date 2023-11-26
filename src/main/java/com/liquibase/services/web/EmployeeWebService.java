package com.liquibase.services.web;

import com.liquibase.client_entities.CaseViewModel;
import com.liquibase.client_entities.EmployeeViewModel;
import com.liquibase.entities.Case;
import com.liquibase.entities.Employee;
import com.liquibase.repositories.CaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeWebService extends AbstractEntityWebService<Employee, EmployeeViewModel, Long> {

}
