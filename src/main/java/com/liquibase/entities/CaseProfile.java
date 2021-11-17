package com.liquibase.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "case_profile")
public class CaseProfile implements Serializable {

    @EmbeddedId
    private CaseProfilePK pk;

    public CaseProfile() {
      pk = new CaseProfilePK();
    }


    public CaseProfile(Profile profile, Case aCase) {
        pk = new CaseProfilePK();
        pk.setProfile(profile);
        pk.setACase(aCase);
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
