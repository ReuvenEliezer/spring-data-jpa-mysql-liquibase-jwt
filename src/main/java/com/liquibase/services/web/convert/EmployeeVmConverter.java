package com.liquibase.services.web.convert;

import com.liquibase.client_entities.CaseThinViewModel;
import com.liquibase.client_entities.EmployeeViewModel;
import com.liquibase.client_entities.ProfileViewModel;
import com.liquibase.entities.CaseProfile;
import com.liquibase.entities.Employee;
import com.liquibase.entities.Profile;
import com.liquibase.repositories.CaseProfileDao;
import com.liquibase.repositories.EmployeeDao;
import com.liquibase.repositories.ProfileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeVmConverter extends AbstractEntityVmConverter<Employee, EmployeeViewModel> {


    @Autowired
    private EmployeeDao employeeDao;

    @Override
    protected void setEntity(Employee employee, EmployeeViewModel employeeViewModel) {
        if (employeeViewModel == null || employee == null)
            throw new IllegalArgumentException();
        employee.setEmail(employeeViewModel.getEmail());
        employee.setFirstName(employeeViewModel.getFirstName());
        employee.setLastName(employeeViewModel.getLastName());
    }

    @Override
    protected Employee findById(Long id) {
        return employeeDao.findById(id).get();
    }

    @Override
    protected Employee createInstance() {
        return new Employee();
    }

    @Override
    public EmployeeViewModel convertToVM(Employee employee) {
        if (employee == null) return null;
        EmployeeViewModel employeeViewModel = new EmployeeViewModel();
        employeeViewModel.setId(employee.getId());
        employeeViewModel.setEmail(employee.getEmail());
        employeeViewModel.setFirstName(employee.getFirstName());
        employeeViewModel.setLastName(employee.getLastName());
        return employeeViewModel;
    }

}
