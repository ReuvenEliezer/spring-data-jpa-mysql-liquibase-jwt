package com.liquibase.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liquibase.client_entities.AbstractEntityViewModel;
import com.liquibase.client_entities.CaseViewModel;
import com.liquibase.client_entities.abstractentities.Data;
import com.liquibase.utils.WsAddressConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ExampleController {

    /**
     * https://developpaper.com/deserialization-of-subclasses-in-springboot/
     * @param entity
     */

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping()
    public <T> void example(@RequestBody Data entity) throws JsonProcessingException {
        AbstractEntityViewModel entityViewModel = entity.getEntityViewModel();
//        JavaType javaType = objectMapper.constructType(entityViewModel.getClass());
//        T o = objectMapper.readValue(entityViewModel.toString(), javaType);
//        System.out.println(o);

        System.out.println(entity);
    }


}
