package com.liquibase.client_entities.abstractentities;

import com.liquibase.client_entities.AbstractEntityViewModel;
import com.liquibase.client_entities.CaseViewModel;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor //for deserializer
@AllArgsConstructor
public class Data {

    private String key;
    private AbstractEntityViewModel entityViewModel;

}
