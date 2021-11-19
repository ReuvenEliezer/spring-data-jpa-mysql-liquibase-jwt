package com.liquibase.services.web;

import com.liquibase.client_entities.AbstractEntityViewModel;

import java.util.List;

public interface EntityVmConverter<E, VM extends AbstractEntityViewModel> {

    E convertFromVM(VM vm);

    VM convertToVM(E entity);

    List<E> convertFromVMList(List<VM> vm);

    List<VM> convertToVMList(List<E> entities);


}
