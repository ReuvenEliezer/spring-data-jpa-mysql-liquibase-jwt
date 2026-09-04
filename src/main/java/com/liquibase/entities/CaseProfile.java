package com.liquibase.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

/**
 * Join entity between {@link Case} and {@link Profile}.
 * <p>
 * The associations are {@code LAZY}: callers that need the target entity should go through the
 * projection queries on {@code CaseProfileDao} ({@code findProfilesByCaseId} /
 * {@code findCasesByProfileId}), which fetch it in a single query instead of one select per row.
 * Callers that only need the foreign keys read them off {@link #getId()} with no database access.
 */
@Getter
@ToString
@Entity
@Table(name = "case_profile")
public class CaseProfile implements Serializable {

    @EmbeddedId
    private CaseProfilePK id;

    @MapsId("profileId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profile_id")
    @ToString.Exclude
    private Profile profile;

    @MapsId("caseId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "case_id")
    @ToString.Exclude
    private Case aCase;

    protected CaseProfile() {
        id = new CaseProfilePK();
    }

    public CaseProfile(Profile profile, Case aCase) {
        this.profile = profile;
        this.aCase = aCase;
        // populated up front rather than left to @MapsId at flush time, so equals/hashCode are
        // stable while the instance sits in a collection before being persisted
        this.id = new CaseProfilePK(profile.getId(), aCase.getId());
    }

    public Case getCase() {
        return aCase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CaseProfile that = (CaseProfile) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
