package com.liquibase.controllers;

import com.liquibase.client_entities.EmployeeViewModel;
import com.liquibase.services.web.EntityWebService;
import com.liquibase.utils.WsAddressConstants;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(WsAddressConstants.employeeLogicUrl)
public class EmployeeController extends CrudController<EmployeeViewModel, Long> {
    EmployeeController(EntityWebService<EmployeeViewModel, Long> webService) {
        super(webService);
    }
}
