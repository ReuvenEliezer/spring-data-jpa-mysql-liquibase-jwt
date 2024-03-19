package com.liquibase.services.web.convert;

import com.liquibase.client_entities.AbstractEntityViewModel;
import com.liquibase.entities.AbstractEntity;
import org.apache.commons.lang3.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractEntityVmConverter<E extends AbstractEntity, VM extends AbstractEntityViewModel> implements EntityVmConverter<E, VM> {

    @Override
    public E convertFromVM(VM vm) {
        E instance;
        if (vm.getId() == null) {
            instance = createInstance();
        } else {
            instance = findById(vm.getId());
        }
        setEntity(instance, vm);
        return instance;
    }

    @Override
    public List<E> convertFromVMList(List<VM> vmList) {
        List<E> entities = new ArrayList<>();
        for (VM entity : vmList) {
            E e = convertFromVM(entity);
            entities.add(e);
        }
        return entities;
    }

    @Override
    public List<VM> convertToVMList(List<E> entities) {
        List<VM> vmList = new ArrayList<>();
        for (E entity : entities) {
//            VM vm = convertToVM(entity, true);
            VM vm = convertToVM(entity);
            vmList.add(vm);
        }
        return vmList;
    }

    protected abstract E createInstance();

    protected abstract void setEntity(E entity, VM vm);

    protected abstract E findById(Long id);

    @Override
    public VM convertToVM(E entity, boolean includeChildren){
        throw new NotImplementedException();
    }

}
