package com.liquibase.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.liquibase.services.audit.AuditTrailListener;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

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
@EntityListeners(AuditTrailListener.class)
public abstract class AbstractEntity implements Serializable {

//    @Transient
//    protected final Logger logger = LogManager.getLogger(this.getClass());


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
