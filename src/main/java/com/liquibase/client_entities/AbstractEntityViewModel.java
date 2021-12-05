package com.liquibase.client_entities;

import lombok.*;

import java.io.Serializable;



@Getter
@Setter
@ToString
@EqualsAndHashCode
//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "entityTypeName")
//@JsonSubTypes({
//        @JsonSubTypes.Type(value = CaseViewModel.class, name = "CaseViewModel"),
//})
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractEntityViewModel implements Serializable {

    private Long id;


}

