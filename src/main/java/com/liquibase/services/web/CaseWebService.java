package com.liquibase.services.web;

import com.liquibase.client_entities.CaseViewModel;
import com.liquibase.entities.Case;
import com.liquibase.repositories.CaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CaseWebService extends AbstractEntityWebService<Case, CaseViewModel, Long> {

    @Autowired
    private CaseDao caseDao;


    @Override
    protected List<Case> innerFindAll() {
        return caseDao.findAllNonDeleted();
    }

    @Override
    protected void innerDelete(Case entity) {
        entity.setDeleted(true);
        caseDao.save(entity);
    }

    @Override
    protected Case findEntityById(Long id) {
        return caseDao.findNonDeleted(id);
    }

}
