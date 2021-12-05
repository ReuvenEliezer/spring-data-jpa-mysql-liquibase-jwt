package com.liquibase.client_entities;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor //for deserializer
public class CasesThinDto {

    private long id;
    private String name;

}
