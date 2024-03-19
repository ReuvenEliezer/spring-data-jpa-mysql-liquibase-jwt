package com.liquibase.services.web.convert;

import com.liquibase.client_entities.CaseViewModel;
import com.liquibase.client_entities.ProfileViewModel;
import com.liquibase.entities.Case;
import com.liquibase.entities.CaseProfile;
import com.liquibase.entities.Profile;
import com.liquibase.repositories.CaseDao;
import com.liquibase.repositories.CaseProfileDao;
import com.liquibase.repositories.ProfileDao;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CaseVmConverter extends AbstractEntityVmConverter<Case, CaseViewModel> {

    private final CaseDao caseDao;

    private final CaseProfileDao caseProfileDao;

    private final ProfileVmConverter profileVmConverter;

    public CaseVmConverter(CaseDao caseDao,
                           CaseProfileDao caseProfileDao,
                           ProfileVmConverter profileVmConverter) {
        this.caseDao = caseDao;
        this.caseProfileDao = caseProfileDao;
        this.profileVmConverter = profileVmConverter;
    }


    @Override
    protected void setEntity(Case caseEntity, CaseViewModel caseViewModel) {
        if (caseViewModel == null || caseEntity == null)
            throw new IllegalArgumentException();
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
        return convertToVM(entity, false);
    }

    @Override
    public CaseViewModel convertToVM(Case entity, boolean includeChildren) {
//    public CaseViewModel convertToVM(Case entity) {
        if (entity == null) return null;
        CaseViewModel caseViewModel = new CaseViewModel();
        caseViewModel.setName(entity.getName());
        caseViewModel.setId(entity.getId());
        if (includeChildren) {
            List<CaseProfile> allByCase = caseProfileDao.getAllByCase(entity.getId());
            List<ProfileViewModel> profileList = allByCase.stream()
                    .map(CaseProfile::getProfile)
                    .map(profileVmConverter::convertToVM)
                    .sorted(Comparator.comparing(ProfileViewModel::getName))
                    .toList();
            caseViewModel.setProfileList(profileList);
        }
        return caseViewModel;
    }


}
