package com.liquibase.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Composite identifier of {@link CaseProfile}, holding the two foreign key values as scalars.
 * <p>
 * The associations themselves live on the entity and are wired to these fields with
 * {@code @MapsId} - same arrangement as {@link CourseRatingKey} / {@link CourseRating}.
 * Keeping entity references out of the identifier is what makes the key cheap and stable:
 * <ul>
 *     <li>key equality compares two {@code Long}s, so it cannot be broken by a Hibernate proxy
 *         (an id class whose equals delegates to an entity fails {@code getClass()} comparison
 *         against {@code Profile$HibernateProxy$...});</li>
 *     <li>the associations are free to be {@code LAZY} - an identifier made of entities has to be
 *         materialised eagerly to build the {@code EntityKey}, whatever fetch type is declared;</li>
 *     <li>the foreign keys are readable off a loaded {@code CaseProfile} with no database access.</li>
 * </ul>
 * A primary key is immutable by contract - mutating it on a managed entity corrupts the
 * persistence context - so there are no setters here.
 */
@Embeddable
@Getter
@EqualsAndHashCode
@ToString
public class CaseProfilePK implements Serializable {

    @Column(name = "profile_id")
    private Long profileId;

    @Column(name = "case_id")
    private Long caseId;

    protected CaseProfilePK() {
    }

    public CaseProfilePK(Long profileId, Long caseId) {
        this.profileId = profileId;
        this.caseId = caseId;
    }

}
