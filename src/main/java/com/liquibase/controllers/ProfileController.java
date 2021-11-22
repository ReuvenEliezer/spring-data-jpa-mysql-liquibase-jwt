package com.liquibase.controllers;

import com.liquibase.client_entities.CaseViewModel;
import com.liquibase.client_entities.ProfileViewModel;
import com.liquibase.entities.CaseProfile;
import com.liquibase.repositories.CaseProfileDao;
import com.liquibase.services.web.convert.CaseVmConverter;
import com.liquibase.utils.WsAddressConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(WsAddressConstants.profileLogicUrl)
public class ProfileController extends CrudController<ProfileViewModel, Long> {

    @Autowired
    private CaseProfileDao caseProfileDao;

    @Autowired
    private CaseVmConverter caseVmConverter;

    @GetMapping(value = "/getAllCases/{profileId}")
    public List<CaseViewModel> getAllCasesByProfile(@PathVariable Long profileId) {
        List<CaseProfile> allByProfile = caseProfileDao.getAllByProfile(profileId);
        return caseVmConverter.convertToVMList(allByProfile.stream()
                .map(CaseProfile::getCase)
                .collect(Collectors.toList()));
    }

}
