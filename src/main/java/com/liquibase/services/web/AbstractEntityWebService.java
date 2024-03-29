package com.liquibase.services.web;

import com.liquibase.client_entities.AbstractEntityViewModel;
import com.liquibase.entities.AbstractEntity;
import com.liquibase.services.transactional.TransactionalOperationsUtil;
import com.liquibase.services.web.convert.EntityVmConverter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractEntityWebService<E extends AbstractEntity, VM extends AbstractEntityViewModel, ID> implements EntityWebService<VM, ID> {

    private final EntityVmConverter<E, VM> converter;

    //    protected SimpleJpaRepository<E, ID> jpaRepository;
    private final JpaRepository<E, ID> jpaRepository;

    private final TransactionalOperationsUtil transactionalOperationsUtil;

    AbstractEntityWebService(EntityVmConverter<E, VM> converter,
                             JpaRepository<E, ID> jpaRepository,
                             TransactionalOperationsUtil transactionalOperationsUtil) {
        this.converter = converter;
        this.jpaRepository = jpaRepository;
        this.transactionalOperationsUtil = transactionalOperationsUtil;
    }

    @Override
    public VM saveOrUpdate(VM vm) {
        E entity = transactionalOperationsUtil.invokeTransactional(() -> {
            E e = converter.convertFromVM(vm);
            if (e == null)
                return null;
            return innerSave(e, vm);
        });
        return converter.convertToVM(entity, true);
//        return converter.convertToVM(entity);
    }

    protected E innerSave(E entity, VM vm) {
        return jpaRepository.save(entity);
    }

    @Override
    public VM findById(ID id) {
        E entity = findEntityById(id);
        return converter.convertToVM(entity, true);
//        return converter.convertToVM(entity);
    }

    @Override
    public final List<VM> findAll() {
        List<E> all = innerFindAll();
        return converter.convertToVMList(all);
    }

    @Override
    public void delete(ID id) {
        transactionalOperationsUtil.invokeTransactional(() -> {
            E entity = findEntityById(id);
            innerDelete(entity);
            return null;
        });
    }

    @Override
    public final List<VM> findAll(Pageable pageable) {
        List<E> all = innerFindAll(pageable);
        return converter.convertToVMList(all);
    }

    protected List<E> innerFindAll() {
        return jpaRepository.findAll();
    }

    protected List<E> innerFindAll(Pageable pageable) {
        return jpaRepository.findAll(pageable).stream()
                .collect(Collectors.toList());
    }

    protected void innerDelete(E entity) {
        jpaRepository.delete(entity);
    }

    protected E findEntityById(ID id) {
        return jpaRepository.findById(id)
//                .orElseThrow(EntityNotFoundException::new);
                .orElseThrow(() -> new EntityNotFoundException(String.format("EntityId '%s' not found in db", id)));
    }
}
