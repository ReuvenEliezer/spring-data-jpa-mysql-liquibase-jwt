package com.liquibase.services.web;

import com.liquibase.client_entities.AbstractEntityViewModel;
import com.liquibase.entities.AbstractEntity;
import com.liquibase.services.transactional.TransactionalOperationsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public abstract class AbstractEntityWebService<E extends AbstractEntity, VM extends AbstractEntityViewModel, ID> implements EntityWebService<VM, ID> {

    @Autowired
    protected EntityVmConverter<E, VM> converter;

    @Autowired
    protected JpaRepository<E, ID> jpaRepository;

    @Autowired
    private TransactionalOperationsUtil transactionalOperationsUtil;


    @Override
    public VM saveOrUpdate(VM vm) {
        E entity = transactionalOperationsUtil.invokeTransactional(() -> {
            E e = converter.convertFromVM(vm);
            return jpaRepository.save(e);
        });
        return converter.convertToVM(entity);
    }

    @Override
    public VM findById(ID id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        E entity = findEntityById(id);
        return converter.convertToVM(entity);
    }

    @Override
    public final List<VM> findAll() {
        List<E> all = innerFindAll();
        return converter.convertToVMList(all);
    }

    protected List<E> innerFindAll() {
        return jpaRepository.findAll();
    }

    @Override
    public void delete(ID id) {
        transactionalOperationsUtil.invokeTransactional(() -> {
            E entity = findEntityById(id);
            innerDelete(entity);
            return null;
        });
    }

    protected void innerDelete(E entity) {
        jpaRepository.delete(entity);
    }

    protected E findEntityById(ID id) {
        return jpaRepository.findById(id)
//                .orElseThrow(EntityNotFoundException::new);
                .orElseThrow(() -> new EntityNotFoundException(String.format("Entity %s not found - ", id)));
    }
}
