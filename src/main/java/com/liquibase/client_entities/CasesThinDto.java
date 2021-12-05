package com.liquibase.client_entities;

import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor //for deserializer
public class CasesThinDto extends AbstractEntityViewModel {

    private String name;

    public CasesThinDto(Long id, String name) {
        super(id);
        this.name = name;
    }
}
