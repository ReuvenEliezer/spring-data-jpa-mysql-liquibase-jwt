package com.liquibase.repositories;

import com.liquibase.entities.CaseProfile;
import com.liquibase.entities.CaseProfilePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CaseProfileDao extends JpaRepository<CaseProfile, CaseProfilePK> {

    @Query("select u from #{#entityName} u where u.pk.profile.id = ?1")
    List<CaseProfile> getAllByProfile(Long profileId);

    @Query("select u from #{#entityName} u where u.pk.aCase.id = ?1")
    List<CaseProfile> getAllByCase(Long caseId);

    @Query("select u from #{#entityName} u where u.pk.aCase.id = ?1 and u.pk.profile.id = ?2")
    CaseProfile getCaseProfile(Long caseId, Long profileId);

//    @Query("select count(u) from #{#entityName} u where u.pk.profile.id = ?1")
    //    @Query("select count(u) > 0 from #{#entityName} u where u.pk.profile.id = ?1")
    @Query("SELECT CASE WHEN count(u) > 0 THEN true ELSE false END FROM #{#entityName} u where u.pk.profile.id = ?1")
    boolean isProfileRelatedToAnyCase(Long profileId);

}
