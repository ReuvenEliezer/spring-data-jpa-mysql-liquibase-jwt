package com.liquibase.client_entities;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class EmployeeViewModel extends AbstractEntityViewModel {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
}
