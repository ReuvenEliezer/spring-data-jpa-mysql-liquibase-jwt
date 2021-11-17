package com.liquibase.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "case_profile")
@Getter
@Setter
public class CaseProfile implements Serializable {

    @EmbeddedId
    private CaseProfilePK pk;

    public CaseProfile() {
        this.pk = new CaseProfilePK();
    }


    public CaseProfile(Profile profile, Case aCase) {
        new CaseProfile();
        pk.setProfile(profile);
        pk.setACase(aCase);
    }

    public CaseProfilePK getPk() {
        return pk;
    }


    public Case getCase() {
        return pk.getACase();
    }

    public void setCase(Case aCase) {
        pk.setACase(aCase);
    }

    public Profile getProfile() {
        return pk.getProfile();
    }

    public void setCase(Profile profile) {
        pk.setProfile(profile);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CaseProfile that = (CaseProfile) o;
        return Objects.equals(pk, that.pk);
    }

    @Override
    public int hashCode() {
        return pk.hashCode();
    }
}
