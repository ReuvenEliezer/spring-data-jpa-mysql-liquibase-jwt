package com.liquibase.services.web.convert;

import com.liquibase.client_entities.CaseViewModel;
import com.liquibase.client_entities.ProfileViewModel;
import com.liquibase.entities.Case;
import com.liquibase.entities.CaseProfile;
import com.liquibase.repositories.CaseDao;
import com.liquibase.repositories.CaseProfileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CaseVmConverter extends AbstractEntityVmConverter<Case, CaseViewModel> {


    @Autowired
    private CaseDao caseDao;

    @Autowired
    private CaseProfileDao caseProfileDao;

    @Autowired
    private ProfileVmConverter profileVmConverter;


    @Override
    protected void setEntity(Case caseEntity, CaseViewModel caseViewModel) {
        caseEntity.setName(caseViewModel.getName());
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
    public CaseViewModel convertToVM(Case entity) {
        CaseViewModel caseViewModel = new CaseViewModel();
        caseViewModel.setName(entity.getName());
        caseViewModel.setId(entity.getId());

        List<CaseProfile> allByCase = caseProfileDao.getAllByCase(entity.getId());
        List<ProfileViewModel> profileList = allByCase.stream()
                .map(CaseProfile::getProfile)
                .map(profileVmConverter::convertToVM)
                .collect(Collectors.toList());
        caseViewModel.setProfileList(profileList);
//        List<Profile> profileList = allByCase.stream().map(CaseProfile::getProfile).collect(Collectors.toList());
//        caseViewModel.setProfileList(profileVmConverter.convertToVMList(profileList));
        return caseViewModel;
    }


}
