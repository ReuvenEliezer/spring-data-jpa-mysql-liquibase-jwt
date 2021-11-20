package com.liquibase.services.web.convert;

import com.liquibase.client_entities.AbstractEntityViewModel;
import com.liquibase.entities.AbstractEntity;

import java.util.List;

public interface EntityVmConverter<E extends AbstractEntity, VM extends AbstractEntityViewModel> {

    E convertFromVM(VM vm);

    VM convertToVM(E entity);

    List<E> convertFromVMList(List<VM> vm);

    List<VM> convertToVMList(List<E> entities);


}
