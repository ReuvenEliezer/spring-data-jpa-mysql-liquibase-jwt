package com.liquibase.client_entities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;

import java.io.Serializable;


//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "entityTypeName")
//@JsonSubTypes({
//        @JsonSubTypes.Type(value = CaseViewModel.class, name = "CaseViewModel"),
//})
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        visible = false)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CaseViewModel.class, name = "CaseViewModel"),
//        @JsonSubTypes.Type(value = CaseThinViewModel.class, name = "CaseThinViewModel"),
        @JsonSubTypes.Type(value = ProfileViewModel.class, name = "ProfileViewModel"),
        @JsonSubTypes.Type(value = EmployeeViewModel.class, name = "EmployeeViewModel"),
})
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractEntityViewModel implements Serializable {

    @Getter
    private Long id;

}

