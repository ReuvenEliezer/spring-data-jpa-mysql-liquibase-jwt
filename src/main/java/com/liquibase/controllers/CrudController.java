package com.liquibase.controllers;

import com.liquibase.client_entities.AbstractEntityViewModel;
import com.liquibase.services.web.EntityWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class CrudController<VM extends AbstractEntityViewModel, ID> {

    @Autowired
    protected EntityWebService<VM, ID> webService;


    @PostMapping()
    public VM saveOrUpdate(@RequestBody VM entity) {
        return webService.saveOrUpdate(entity);
    }

    @GetMapping(value = "{id}")
    public VM findById(@PathVariable ID id) {
        return webService.findById(id);
    }

    @GetMapping(value = "findAll")
    public List<VM> findAll() {
        return webService.findAll();
    }

    @DeleteMapping(value = "{id}")
    public void delete(@PathVariable ID id) {
        webService.delete(id);
    }
}
