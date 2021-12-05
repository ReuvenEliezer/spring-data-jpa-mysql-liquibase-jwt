package com.liquibase.client_entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class CaseViewModel extends AbstractEntityViewModel {

    private String name;
    private List<ProfileViewModel> profileList = new ArrayList<>();

}
