package com.liquibase.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * https://thorben-janssen.com/complete-guide-inheritance-strategies-jpa-hibernate/
 */
@Inheritance(strategy = InheritanceType.JOINED)

//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
//@JsonSubTypes({ //for sub abstract class
//        @JsonSubTypes.Type(value = Case.class, name = "Case"),
//        @JsonSubTypes.Type(value = Case.class, name = "BaseEntity"),
//})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id") // for serialization
@Getter
@ToString
@Entity
@Table(name = "abstract_entity")
public abstract class AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractEntity that = (AbstractEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
