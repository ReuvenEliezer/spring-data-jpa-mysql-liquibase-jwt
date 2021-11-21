package com.liquibase.services.web.convert;

import com.liquibase.client_entities.CaseViewModel;
import com.liquibase.client_entities.ProfileViewModel;
import com.liquibase.entities.Case;
import com.liquibase.entities.CaseProfile;
import com.liquibase.entities.Profile;
import com.liquibase.repositories.CaseDao;
import com.liquibase.repositories.CaseProfileDao;
import com.liquibase.repositories.ProfileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CaseVmConverter extends AbstractEntityVmConverter<Case, CaseViewModel> {


    @Autowired
    private CaseDao caseDao;

    @Autowired
    private ProfileDao profileDao;

    @Autowired
    private CaseProfileDao caseProfileDao;

    @Autowired
    private ProfileVmConverter profileVmConverter;


    @Override
    protected void setEntity(Case caseEntity, CaseViewModel caseViewModel) {
        if (caseViewModel == null || caseEntity==null)
            throw new IllegalArgumentException();
        caseEntity.setName(caseViewModel.getName());

        handleProfiles(caseEntity, caseViewModel);
    }

    private void handleProfiles(Case caseEntity, CaseViewModel caseViewModel) {
        Set<Profile> profileList = caseViewModel.getProfileList().stream()
                .map(profileVmConverter::convertFromVM)
                .collect(Collectors.toSet());

        Set<Profile> profileNonExistInDbSet = new HashSet<>();
        for (Profile profile : profileList) {
            if (profile.getId() == null || !profileDao.findById(profile.getId()).isPresent()) {
                //TODO error profile not exist in db
                profileNonExistInDbSet.add(profile);
            }
        }
        profileList.removeAll(profileNonExistInDbSet);

        if (profileList.isEmpty())
            return;

        Set<CaseProfile> alreadyExists = caseProfileDao.getAllByCase(caseEntity.getId()).stream()
//                .map(CaseProfile::getProfile)
                .collect(Collectors.toSet());

        Set<CaseProfile> caseProfilesToDelete = alreadyExists.stream()
                .filter(caseProfile -> !profileList.contains(caseProfile.getProfile()))
                .collect(Collectors.toSet());
        if (!caseProfilesToDelete.isEmpty())
            caseProfileDao.deleteAll(caseProfilesToDelete);


        Set<CaseProfile> caseProfiles = profileList.stream()
                .filter(profile -> !alreadyExists.contains(profile))
                .map(profile -> new CaseProfile(profile, caseEntity))
                .collect(Collectors.toSet());
        if (!caseProfiles.isEmpty())
            caseProfileDao.saveAll(caseProfiles);
    }

    @Override
    protected Case findById(Long id) {
        return caseDao.findNonDeleted(id);
    }

    @Override
    protected Case createInstance() {
        return new Case();
    }

    @Override
    public CaseViewModel convertToVM(Case entity, boolean includeChildren) {
        if (entity == null) return null;
        CaseViewModel caseViewModel = new CaseViewModel();
        caseViewModel.setName(entity.getName());
        caseViewModel.setId(entity.getId());
        if (includeChildren) {
            List<CaseProfile> allByCase = caseProfileDao.getAllByCase(entity.getId());
            List<ProfileViewModel> profileList = allByCase.stream()
                    .map(CaseProfile::getProfile)
                    .map(e -> profileVmConverter.convertToVM(e, false))
                    .collect(Collectors.toList());
            caseViewModel.setProfileList(profileList);
        }
        return caseViewModel;
    }


}
