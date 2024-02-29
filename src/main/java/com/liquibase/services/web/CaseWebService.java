package com.liquibase.services.web;

import com.liquibase.client_entities.CaseViewModel;
import com.liquibase.entities.Case;
import com.liquibase.repositories.CaseDao;
import com.liquibase.services.transactional.TransactionalOperationsUtil;
import com.liquibase.services.web.convert.EntityVmConverter;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CaseWebService extends AbstractEntityWebService<Case, CaseViewModel, Long> {

    private final CaseDao caseDao;

    CaseWebService(EntityVmConverter<Case, CaseViewModel> converter,
                   JpaRepository<Case, Long> jpaRepository,
                   TransactionalOperationsUtil transactionalOperationsUtil,
                   CaseDao caseDao) {
        super(converter, jpaRepository, transactionalOperationsUtil);
        this.caseDao = caseDao;
    }


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

    protected List<Case> innerFindAll(Pageable pageable) {
        /**
         * Example.of is return all cases that isDeleted==false, because of the default value of Case entity is isDeleted=false
         *
         * https://stackoverflow.com/questions/39823333/use-cases-of-methods-of-querybyexampleexecutort-interface-in-spring-data-jpa
         *
         * new Case for find the entities that similar to Case with isDeleted=false, in order to not return the cases entities that mark as deleted
         */
        return caseDao.findAll(Example.of(new Case()), pageable).getContent();
    }

}
