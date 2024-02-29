package com.liquibase.client_entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class EmployeeViewModel extends AbstractEntityViewModel {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
}
