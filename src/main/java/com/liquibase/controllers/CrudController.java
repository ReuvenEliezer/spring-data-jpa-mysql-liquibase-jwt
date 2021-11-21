package com.liquibase.controllers;

import com.liquibase.client_entities.AbstractEntityViewModel;
import com.liquibase.services.web.EntityWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public abstract class CrudController<VM extends AbstractEntityViewModel, ID> {

    @Autowired
    protected EntityWebService<VM, ID> webService;


    @PostMapping(value = "/saveOrUpdate")
    public VM saveOrUpdate(@RequestBody VM entity) {
        return webService.saveOrUpdate(entity);
    }

    @GetMapping(value = "/findById/{id}")
    public VM findById(@PathVariable ID id) {
        return webService.findById(id);
    }

    @GetMapping(value = "/findAll")
    public List<VM> findAll() {
        return webService.findAll();
    }

    @GetMapping(value = "/delete/{id}")
    public void delete(@PathVariable ID id) {
        webService.delete(id);
    }
}
