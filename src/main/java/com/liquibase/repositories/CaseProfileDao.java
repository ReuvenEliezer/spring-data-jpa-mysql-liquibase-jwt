package com.liquibase.repositories;

import com.liquibase.entities.Case;
import com.liquibase.entities.CaseProfile;
import com.liquibase.entities.CaseProfilePK;
import com.liquibase.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CaseProfileDao extends JpaRepository<CaseProfile, CaseProfilePK> {

    /**
     * Projects straight to the target entity: callers of this association want the {@link Profile},
     * not the join row. One query, instead of selecting the join rows and then dereferencing a
     * lazy association per row.
     */
    @Query("select cp.profile from CaseProfile cp where cp.id.caseId = ?1")
    List<Profile> findProfilesByCaseId(Long caseId);

    @Query("select cp.aCase from CaseProfile cp where cp.id.profileId = ?1")
    List<Case> findCasesByProfileId(Long profileId);

    /**
     * The join rows themselves - only needed when they are about to be deleted.
     */
    List<CaseProfile> findAllByIdCaseId(Long caseId);

    Optional<CaseProfile> findByIdCaseIdAndIdProfileId(Long caseId, Long profileId);

    boolean existsByIdProfileId(Long profileId);

}
