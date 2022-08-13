package com.liquibase.client_entities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;

import java.io.Serializable;


@Setter
@ToString
@EqualsAndHashCode
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
        @JsonSubTypes.Type(value = CaseThinViewModel.class, name = "CaseThinViewModel"),
        @JsonSubTypes.Type(value = ProfileViewModel.class, name = "ProfileViewModel"),

})
@NoArgsConstructor
public abstract class AbstractEntityViewModel implements Serializable {

    @Getter
    private Long id;

    private String type;

    public AbstractEntityViewModel(Long id) {
        this.id = id;
    }

    public String getType() {
        return this.getClass().getSimpleName();
    }


}

