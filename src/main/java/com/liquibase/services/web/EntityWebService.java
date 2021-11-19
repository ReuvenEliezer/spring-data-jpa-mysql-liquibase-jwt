package com.liquibase.services.web;

import java.util.List;

public interface EntityWebService<VM, ID> {

    VM saveOrUpdate(VM entity);

    VM findById(ID id);

    List<VM> findAll();

    void delete(ID id);
}
