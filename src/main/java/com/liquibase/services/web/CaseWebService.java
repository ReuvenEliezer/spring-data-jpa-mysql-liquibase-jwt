package com.liquibase.services.web;

import com.liquibase.client_entities.CaseViewModel;
import com.liquibase.entities.Case;
import com.liquibase.entities.CaseProfile;
import com.liquibase.entities.Profile;
import com.liquibase.client_entities.ProfileViewModel;
import com.liquibase.repositories.CaseDao;
import com.liquibase.repositories.CaseProfileDao;
import com.liquibase.services.transactional.TransactionalOperationsUtil;
import com.liquibase.services.web.convert.EntityVmConverter;
import com.liquibase.services.web.convert.ProfileVmConverter;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CaseWebService extends AbstractEntityWebService<Case, CaseViewModel, Long> {

    private final CaseDao caseDao;

    private final CaseProfileDao caseProfileDao;

    private final ProfileVmConverter profileVmConverter;

    CaseWebService(EntityVmConverter<Case, CaseViewModel> converter,
                   JpaRepository<Case, Long> jpaRepository,
                   TransactionalOperationsUtil transactionalOperationsUtil,
                   CaseDao caseDao,
                   CaseProfileDao caseProfileDao,
                   ProfileVmConverter profileVmConverter) {
        super(converter, jpaRepository, transactionalOperationsUtil);
        this.caseDao = caseDao;
        this.caseProfileDao = caseProfileDao;
        this.profileVmConverter = profileVmConverter;
    }


    @Override
    protected List<Case> innerFindAll() {
        return caseDao.findAllNonDeleted();
    }

    @Override
    protected void innerDelete(Case entity) {
        entity.setDeleted(true);
        caseDao.save(entity);
    }

    @Override
    protected Case innerSave(Case aCase, CaseViewModel caseViewModel) {
        Case save = super.innerSave(aCase, caseViewModel);
        handleProfiles(aCase, caseViewModel);
        return save;
    }

    /**
     * Reconciles the case's profile associations with what the view model asks for.
     * <p>
     * Diffing is done on profile ids read off {@link CaseProfile#getId()} rather than on entity
     * equality: the associations are lazy, so {@code getProfile()} can hand back a proxy, and
     * {@code AbstractEntity.equals} compares with {@code getClass()} - a proxy never matches a
     * loaded instance. Reading the id off the key also costs no database access.
     * <p>
     * An empty {@code profileList} means "this case has no profiles" and must still run the
     * deletion pass, otherwise the last association can never be removed.
     */
    private void handleProfiles(Case caseEntity, CaseViewModel caseViewModel) {
        List<ProfileViewModel> profileViewModels = caseViewModel.getProfileList() == null
                ? List.of()
                : caseViewModel.getProfileList();

        Map<Long, Profile> requestedProfilesById = new LinkedHashMap<>();
        for (ProfileViewModel profileViewModel : profileViewModels) {
            Profile profile = profileVmConverter.convertFromVM(profileViewModel);
            if (profile == null || profile.getId() == null) {
                //TODO error profile not exist in db
                continue;
            }
            requestedProfilesById.putIfAbsent(profile.getId(), profile);
        }

        List<CaseProfile> alreadyExists = caseProfileDao.findAllByIdCaseId(caseEntity.getId());

        List<CaseProfile> caseProfilesToDelete = alreadyExists.stream()
                .filter(caseProfile -> !requestedProfilesById.containsKey(caseProfile.getId().getProfileId()))
                .toList();
        if (!caseProfilesToDelete.isEmpty()) {
            caseProfileDao.deleteAll(caseProfilesToDelete);
        }

        Set<Long> profileIdsAlreadyExists = alreadyExists.stream()
                .map(caseProfile -> caseProfile.getId().getProfileId())
                .collect(Collectors.toSet());

        List<CaseProfile> caseProfiles = requestedProfilesById.values().stream()
                .filter(profile -> !profileIdsAlreadyExists.contains(profile.getId()))
                .map(profile -> new CaseProfile(profile, caseEntity))
                .toList();
        if (!caseProfiles.isEmpty()) {
            caseProfileDao.saveAll(caseProfiles);
        }
    }

    @Override
    protected Case findEntityById(Long id) {
        return caseDao.findNonDeleted(id);
    }

    @Override
    protected List<Case> innerFindAll(Pageable pageable) {
        /**
         * Example.of is return all cases that isDeleted==false, because of the default value of Case entity is isDeleted=false
         *
         * https://stackoverflow.com/questions/39823333/use-cases-of-methods-of-querybyexampleexecutort-interface-in-spring-data-jpa
         *
         * new Case for find the entities that similar to Case with isDeleted=false, in order to not return the cases entities that mark as deleted
         */
        return caseDao.findAll(Example.of(new Case()), pageable).getContent();
    }

}
