package com.liquibase.controllers;

import com.liquibase.client_entities.AbstractEntityViewModel;
import com.liquibase.services.web.EntityWebService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class CrudController<VM extends AbstractEntityViewModel, ID> {

    protected final EntityWebService<VM, ID> webService;

    CrudController(EntityWebService<VM, ID> webService) {
        this.webService = webService;
    }

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

    /**
     * http://localhost:<port>/<entity>/findAll/listPageable?page=0&size=3&sort=<columnName>   ->
     *     the default sorting is ASC, if u want to sort by DESC add ",desc" to your request for example:
     *     http://localhost:8080/case/findAll/listPageable?page=0&size=3&sort=name,desc
     *https://docs.spring.io/spring-data/rest/docs/2.0.0.M1/reference/html/paging-chapter.html
     * https://stackoverflow.com/questions/52421182/spring-boot-rest-sort-direction-is-ignored-but-sort-generally-works/52422069
     * https://www.javainuse.com/spring/SpringBootUsingPagination
     */
    @GetMapping(value = "findAll/listPageable")
    List<VM> findAll(Pageable pageable) {
        return webService.findAll(pageable);
    }
}
