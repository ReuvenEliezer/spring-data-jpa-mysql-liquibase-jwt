package com.liquibase.client_entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;



@Getter
@ToString
@EqualsAndHashCode
//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "entityTypeName")
//@JsonSubTypes({
//        @JsonSubTypes.Type(value = CaseViewModel.class, name = "CaseViewModel"),
//})
public abstract class AbstractEntityViewModel implements Serializable {

    private Long id;

}

