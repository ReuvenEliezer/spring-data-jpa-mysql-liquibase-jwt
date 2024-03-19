package com.liquibase.services.web;

import com.liquibase.client_entities.CaseViewModel;
import com.liquibase.entities.Case;
import com.liquibase.entities.CaseProfile;
import com.liquibase.entities.Profile;
import com.liquibase.repositories.CaseDao;
import com.liquibase.repositories.CaseProfileDao;
import com.liquibase.repositories.ProfileDao;
import com.liquibase.services.transactional.TransactionalOperationsUtil;
import com.liquibase.services.web.convert.EntityVmConverter;
import com.liquibase.services.web.convert.ProfileVmConverter;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CaseWebService extends AbstractEntityWebService<Case, CaseViewModel, Long> {

    private final CaseDao caseDao;

    private final CaseProfileDao caseProfileDao;

    private final ProfileVmConverter profileVmConverter;

    private final ProfileDao profileDao;

    CaseWebService(EntityVmConverter<Case, CaseViewModel> converter,
                   JpaRepository<Case, Long> jpaRepository,
                   TransactionalOperationsUtil transactionalOperationsUtil,
                   CaseDao caseDao,
                   CaseProfileDao caseProfileDao,
                   ProfileVmConverter profileVmConverter,
                   ProfileDao profileDao) {
        super(converter, jpaRepository, transactionalOperationsUtil);
        this.caseDao = caseDao;
        this.caseProfileDao = caseProfileDao;
        this.profileVmConverter = profileVmConverter;
        this.profileDao = profileDao;
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

    private void handleProfiles(Case caseEntity, CaseViewModel caseViewModel) {
        Set<Profile> profileList = caseViewModel.getProfileList().stream()
                .map(profileVmConverter::convertFromVM)
                .collect(Collectors.toSet());

        Set<Profile> profileNonExistInDbSet = new HashSet<>();
        for (Profile profile : profileList) {
            if (profile.getId() == null || profileDao.findById(profile.getId()).isEmpty()) {
                //TODO error profile not exist in db
                profileNonExistInDbSet.add(profile);
            }
        }
        profileList.removeAll(profileNonExistInDbSet);

        if (profileList.isEmpty()) {
            return;
        }

        Set<CaseProfile> alreadyExists = new HashSet<>(caseProfileDao.getAllByCase(caseEntity.getId()));

        Set<CaseProfile> caseProfilesToDelete = alreadyExists.stream()
                .filter(caseProfile -> !profileList.contains(caseProfile.getProfile()))
                .collect(Collectors.toSet());
        if (!caseProfilesToDelete.isEmpty()) {
            caseProfileDao.deleteAll(caseProfilesToDelete);
        }

        List<Profile> profileAlreadyExists = alreadyExists.stream().map(CaseProfile::getProfile).toList();
        List<CaseProfile> caseProfiles = profileList.stream()
                .filter(profile -> !profileAlreadyExists.contains(profile))
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
