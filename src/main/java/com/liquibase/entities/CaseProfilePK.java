package com.liquibase.entities;

import lombok.*;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CaseProfilePK implements Serializable {


    /**
     * https://stackoverflow.com/questions/2990799/difference-between-fetchtype-lazy-and-eager-in-java-persistence-api
     * 12
     * By default, for all collection and map objects the fetching rule is FetchType.LAZY and for other instances it follows the FetchType.EAGER policy.
     * In brief, @OneToMany and @ManyToMany relations does not fetch the related objects (collection and map) implicictly but the retrieval operation is cascaded through the field in @OneToOne and @ManyToOne ones.
     */
    @JoinColumn(name = "profile_id",referencedColumnName = "id")
    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    private Profile profile;

    @JoinColumn(name = "case_id",referencedColumnName = "id")
    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    private Case aCase;


}