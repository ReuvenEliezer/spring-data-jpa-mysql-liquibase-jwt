package com.liquibase.services.web.convert;

import com.liquibase.client_entities.EmployeeViewModel;
import com.liquibase.entities.Employee;
import com.liquibase.repositories.EmployeeDao;
import org.springframework.stereotype.Component;

@Component
public class EmployeeVmConverter extends AbstractEntityVmConverter<Employee, EmployeeViewModel> {


    private final EmployeeDao employeeDao;

    public EmployeeVmConverter(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

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
