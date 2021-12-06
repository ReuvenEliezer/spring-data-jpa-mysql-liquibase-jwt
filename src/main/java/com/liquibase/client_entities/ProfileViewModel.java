package com.liquibase.client_entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class ProfileViewModel extends AbstractEntityViewModel {

    private String firstName;
    private String photo;
    //    private List<CaseViewModel> caseList = new ArrayList<>();
    private List<CaseThinViewModel> caseThinViewModelList;


}
