package com.liquibase.services.web;

import com.liquibase.client_entities.AbstractEntityViewModel;

import java.util.List;

public interface EntityWebService<VM extends AbstractEntityViewModel, ID> {

    VM saveOrUpdate(VM entity);

    VM findById(ID id);

    List<VM> findAll();

    void delete(ID id);
}
