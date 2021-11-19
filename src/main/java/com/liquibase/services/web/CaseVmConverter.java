package com.liquibase.services.web;

import com.liquibase.client_entities.CaseViewModel;
import com.liquibase.entities.Case;
import com.liquibase.repositories.CaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CaseVmConverter extends AbstractEntityVmConverter<Case, CaseViewModel> {


    @Autowired
    protected CaseDao caseDao;

    @Override
    protected void setEntity(Case caseEntity, CaseViewModel caseViewModel) {
        caseEntity.setName(caseViewModel.getName());
    }

    @Override
    protected Case findById(Long id) {
        return caseDao.findNonDeleted(id);
    }

    @Override
    protected Case createInstance() {
        return new Case();
    }

    @Override
    public CaseViewModel convertToVM(Case entity) {
        CaseViewModel caseViewModel = new CaseViewModel();
        caseViewModel.setName(entity.getName());
        caseViewModel.setId(entity.getId());
        return caseViewModel;
    }


}
