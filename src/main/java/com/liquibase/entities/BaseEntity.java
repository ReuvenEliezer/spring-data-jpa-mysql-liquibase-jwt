package com.liquibase.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@ToString(callSuper = true)
@Entity
@Table(name = "base_entity")
@JsonIgnoreProperties(value = {"created_at", "modified_at", "created_by", "modified_by"}, allowGetters = true)
@EntityListeners(AuditingEntityListener.class)
/**
 * https://stackoverflow.com/questions/49954812/how-can-you-make-a-created-at-column-generate-the-creation-date-time-automatical
 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#:~:text=the%20following%20example%3A-,Example%20116.%20Auditing%20configuration%20orm.xml,-%3Cpersistence%2Dunit%2Dmetadata
 */
//@JsonSubTypes({ //for sub abstract class
//        @JsonSubTypes.Type(value = Case.class, name = "Case"),
//})
//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")

public abstract class BaseEntity extends AbstractEntity {

/**
 * SELECT *
 * FROM netapp.cases
 * INNER JOIN netapp.base_entity  ON netapp.base_entity.id=netapp.cases.id;
 */

    @CreatedDate
    @NotNull
    @Column(name = "created_at", updatable = false)
    @ToString.Exclude
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "modified_at")
    @ToString.Exclude
    private LocalDateTime modifiedAt;

    @CreatedBy
    @Column(name = "created_by")
    @ToString.Exclude
    private String createdBy;

    @LastModifiedBy
    @Column(name = "modified_by")
    @ToString.Exclude
    private String modifiedBy;

}
